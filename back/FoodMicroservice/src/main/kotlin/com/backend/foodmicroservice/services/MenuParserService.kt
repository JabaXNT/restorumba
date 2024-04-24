package com.backend.foodmicroservice.services

import com.backend.foodmicroservice.models.City
import com.backend.foodmicroservice.models.CityRestaurant
import com.backend.foodmicroservice.models.Food
import com.backend.foodmicroservice.models.Restaurant
import com.backend.foodmicroservice.repositories.CityRepository
import com.backend.foodmicroservice.repositories.CityRestaurantRepository
import com.backend.foodmicroservice.repositories.FoodRepository
import com.backend.foodmicroservice.repositories.RestaurantRepository
import kotlinx.serialization.json.*
import org.springframework.http.MediaType
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import org.slf4j.LoggerFactory
import kotlinx.coroutines.*
import org.springframework.web.reactive.function.client.bodyToMono
import java.time.LocalDateTime

@Service
class MenuParserService
    (private val cityRepo: CityRepository,
     private val foodRepo: FoodRepository,
     private val restaurantRepo: RestaurantRepository,
     private val cityRestaurantRepo: CityRestaurantRepository
    ) {


    private val logger = LoggerFactory.getLogger(MenuParserService::class.java)
    private val cityCache = mutableMapOf<String, City?>()
    private final val exchangeStrategies = ExchangeStrategies.builder()
        .codecs { configurer: ClientCodecConfigurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 100) } // 50MB
        .build()

    val webClient = WebClient.builder()
        .exchangeStrategies(exchangeStrategies)
        .build()

    private fun parseMenuWebClientRequest(url: String): WebClient.RequestHeadersSpec<*> {
        return webClient.get()
            .uri(url)
            .header(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537"
            )
            .accept(MediaType.APPLICATION_JSON)
    }

    private fun createNewFood(name: String?, description: String?, price: Double?, uri: String?, category: JsonElement, restaurant: Restaurant, weight: String?): Food {
        return Food(
            foodId = 0,
            restaurant = restaurant,
            name = name ?: "",
            category = category.jsonObject["name"]?.jsonPrimitive?.content ?: "",
            defaultPrice = price ?: 0.0,
            weight = weight ?: "",
            description = description ?: "",
            imageUrl = uri ?: ""
        )
    }

    fun parseMenu(cityName: String) {
        logger.info("Start processing menu parsing for city:$cityName")
        val city = cityRepo.findByName(cityName)
        if (city != null) {
            val batchSize = 100
            val cityRestaurants = city.cityRestaurants.chunked(batchSize)
            cityRestaurants.forEach { batch ->
                runBlocking {
                    val deferred = batch.map { cityRestaurant ->
                        async(Dispatchers.IO) {
                            val restaurant = cityRestaurant.restaurant
                            val url = "https://eda.yandex.ru/api/v2/menu/retrieve/${restaurant.slug}?regionId=9&autoTranslate=false"
                            val request = parseMenuWebClientRequest(url)
                            try {
                                val response = request.retrieve().bodyToMono<String>().block()
                                if (response != null) {
                                    processMenuResponse(response, restaurant)
                                }
                            } catch (e: Exception) {
                                logger.error("Error processing restaurant: ${restaurant.name}", e)
                            }
                        }
                    }
                    deferred.awaitAll()
                }
            }
            logger.warn("Parsing done")
        } else {
            logger.warn("City not found: $cityName")
        }
    }

    private fun processMenuResponse(response: String, restaurant: Restaurant) {
        val data = Json.parseToJsonElement(response).jsonObject
        val payload = data["payload"]?.jsonObject
        val categories = payload?.get("categories")?.jsonArray ?: return

        val foods = mutableListOf<Food>()

        categories.forEach { category ->
            category.jsonObject["items"]?.jsonArray?.let { items ->
                items.forEach { item ->
                    val itemObj = item.jsonObject
                    val name = itemObj["name"]?.jsonPrimitive?.content
                    val description = itemObj["description"]?.jsonPrimitive?.content
                    val price = itemObj["price"]?.jsonPrimitive?.double
                    val measure = itemObj["weight"]?.jsonPrimitive?.content
                    val pictureObj = itemObj["picture"]?.jsonObject
                    val uri = pictureObj?.get("uri")?.jsonPrimitive?.content

                    if (name != null && price != null && uri != null) {
                        if (!foodRepo.existsByNameAndRestaurantAndCategoryAndDescriptionAndDefaultPriceAndImageUrl(
                                name,
                                restaurant,
                                category.jsonObject["name"]?.jsonPrimitive?.content ?: "",
                                description ?: "",
                                price,
                                uri
                            )) {
                            val food = createNewFood(name, description, price, uri, category, restaurant, measure)
                            foods.add(food)
                        }
                    }
                }
            }
        }
        if (foods.isNotEmpty()) {
            foodRepo.saveAll(foods)
            logger.info("Saved/Updated ${foods.size} food items for restaurant: ${restaurant.name}")
        } else {
            logger.info("No new food items found for restaurant: ${restaurant.name}")
        }
    }

    private fun parseLayoutWebClientRequest(coords: List<Double>): WebClient.RequestHeadersSpec<*> {
        val url = "https://eda.yandex.ru/eats/v1/layout-constructor/v1/layout"
        val headers = mapOf(
            "Content-Type" to "application/json",
            "X-Device-Id" to "luoguuoq-56772dfuw9g-shish-cringe"
        )
        val body = mapOf(
            "location" to mapOf(
                "latitude" to coords[1],
                "longitude" to coords[0]
            )
        )

        return webClient.post()
            .uri(url)
            .headers { it.setAll(headers) }
            .body(BodyInserters.fromValue(body))
            .accept(MediaType.APPLICATION_JSON)
    }

    private fun processResponse(response: String?, cityName: String) {
        logger.info("Start processing city-restaurant relationship for city:$cityName")
        val data = Json.parseToJsonElement(response ?: return).jsonObject
        val placesLists = data["data"]?.jsonObject?.get("places_lists")?.jsonArray ?: return
        var city = cityRepo.findByName(cityName)
        if (city == null) {
            city = City(cityId = 0, name = cityName)
            cityRepo.save(city)
            logger.info("Saved new city: $cityName")
        }
        cityCache[cityName] = city
        val cityRestaurantsToSave = mutableListOf<CityRestaurant>()
        placesLists.forEach { payload ->
            logger.warn(payload.jsonObject["payload"]?.jsonObject?.get("places")?.jsonArray?.size.toString())
            payload.jsonObject["payload"]?.jsonObject?.get("places")?.jsonArray?.forEach { place ->
                val placeObj = place.jsonObject
                val slugName = placeObj["slug"]?.jsonPrimitive?.content
                val restaurantName = placeObj["name"]?.jsonPrimitive?.content
                if (slugName != null && restaurantName != null) {
                    var restaurant = restaurantRepo.findByName(restaurantName)
                    if (restaurant == null) {
                        restaurant = Restaurant(
                            restaurantId = 0,
                            name = restaurantName,
                            lastUpdated = LocalDateTime.now(),
                            foods = mutableListOf(),
                            slug = slugName
                        )
                        restaurant = restaurantRepo.save(restaurant)
                    }
                    if (!cityRestaurantRepo.existsByCityAndRestaurant(city, restaurant)) {
                        val cityRestaurant = CityRestaurant(
                            id = 0,
                            city = city,
                            restaurant = restaurant
                        )
                        cityRestaurantsToSave.add(cityRestaurant)
                        cityRestaurantRepo.save(cityRestaurant)
                    }
                }
            }
        }
        if (cityRestaurantsToSave.isNotEmpty()) {
            logger.info("Saved ${cityRestaurantsToSave.size} new city-restaurant relationships for city: $cityName")
        } else {
            logger.info("No new restaurants found for city: $cityName")
        }
    }

    fun parseLayout(cityCoords: Result<List<Double>>, cityName: String) {
        if (cityCoords.isFailure) {
            logger.error("Failed to get city coordinates: ${cityCoords.exceptionOrNull()?.message}")
            return
        }

        val coords = cityCoords.getOrNull() ?: return
        val request = parseLayoutWebClientRequest(coords)
        val response = request.retrieve().bodyToMono(String::class.java).block()

        processResponse(response, cityName)
    }

    fun yandexApi(city: String): Result<List<Double>> {
        val apikey = "46a0725b-6688-46a2-ab20-e711a47a8a77"
        val mapsUrl = "https://geocode-maps.yandex.ru/1.x/?apikey=$apikey&geocode=$city&format=json"

        val apiResponse: String? = webClient.get()
            .uri(mapsUrl)
            .retrieve()
            .bodyToMono(String::class.java)
            .block()

        if (apiResponse == null) {
            return Result.failure(Exception("API response is null"))
        }

        val data = Json.parseToJsonElement(apiResponse).jsonObject

        val pointPosition = data["response"]?.jsonObject?.get("GeoObjectCollection")?.jsonObject?.get("featureMember")?.jsonArray
        if (pointPosition.isNullOrEmpty()) {
            return Result.failure(Exception("Point position is null or empty"))
        }

        val geoObject = pointPosition[0].jsonObject["GeoObject"]?.jsonObject
        val pos = geoObject?.get("Point")?.jsonObject?.get("pos")?.jsonPrimitive?.content
        val coordinates = pos!!.split(" ").map { it.toDouble() }

        return Result.success(coordinates)
    }

//    @Scheduled(initialDelay = 1000)
//    fun testing() {
//        val coords = yandexApi("Белоярский")
//        parseLayout(coords, "Белоярский")
//        parseMenu("Kazan")
//    }
}