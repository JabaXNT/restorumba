package com.backend.foodmicroservice.services

import com.backend.foodmicroservice.models.Food
import com.backend.foodmicroservice.repositories.FoodRepository
import jakarta.annotation.PostConstruct
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import kotlin.random.Random

@Service
class RandomFoodService
    (private val foodRepository: FoodRepository,
     private val redisTemplate: RedisTemplate<String, List<Food>>) {

    private lateinit var valueOps: ValueOperations<String, List<Food>>

    @PostConstruct
    private fun init() {
        valueOps = redisTemplate.opsForValue()
    }

    fun getRandomFood(restaurantId: Long): String {
        val redisKey = "restaurant:$restaurantId:foods"
        var foods = valueOps[redisKey]
        if (foods == null) {
            foods = foodRepository.findAllByRestaurant_RestaurantId(restaurantId)
            valueOps[redisKey] = foods
        }
        if (foods.isEmpty()) {
            return "No food found for the given restaurant"
        }
        val randomFood = foods[Random.nextInt(foods.size)]
        return "Random food from restaurant: ${randomFood.name}"
    }

    private final val exchangeStrategies = ExchangeStrategies.builder()
        .codecs { configurer: ClientCodecConfigurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 100) } // 50MB
        .build()

    val webClient = WebClient.builder()
        .exchangeStrategies(exchangeStrategies)
        .build()

    fun getRestaurantByCoords(latitude: Double, longitude: Double): String {
        val apikey = "46a0725b-6688-46a2-ab20-e711a47a8a77"
        val request = "https://geocode-maps.yandex.ru/1.x/?apikey=$apikey&geocode=Быстрое питание&ll=$longitude,$latitude&type=restaurant&results=1&format=json"
        val apiResponse: String? = webClient.get()
            .uri(request)
            .retrieve()
            .bodyToMono(String::class.java)
            .block()

        if (apiResponse == null) {
            return "API response is null"
        }
        println(apiResponse)
        val data = Json.parseToJsonElement(apiResponse).jsonObject
        val featureMember = data["response"]?.jsonObject?.get("GeoObjectCollection")?.jsonObject?.get("featureMember")?.jsonArray
        if (featureMember.isNullOrEmpty()) {
            return "No restaurant found at the given coordinates"
        }

        val geoObject = featureMember[0].jsonObject["GeoObject"]?.jsonObject
        val restaurantName = geoObject?.get("name")?.jsonPrimitive?.content

        return restaurantName ?: "No restaurant name found in the API response"
    }

//    @Scheduled(initialDelay = 1000)
    fun testing() {
        println(getRestaurantByCoords( 63.716043, 66.667597))
    }
}