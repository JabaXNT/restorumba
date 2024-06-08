package com.backend.foodmicroservice.controllers

import com.backend.foodmicroservice.models.Food
import com.backend.foodmicroservice.services.RandomFoodService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/restorumba")
class RandomFoodController(private val randomFoodService: RandomFoodService) {

    data class FoodResponse(val name: String)


    @GetMapping("/randomFrom/{restaurantId}")
    fun parseMenu(@PathVariable restaurantId: Long): ResponseEntity<String> {
        val randomFood = randomFoodService.getRandomFood(restaurantId)
        return ResponseEntity.ok(randomFood)
    }

    @PostMapping("/location")
    fun printLocation(@RequestParam latitude: Double, @RequestParam longitude: Double): ResponseEntity<String> {
        println("User's current location is: Latitude = $latitude, Longitude = $longitude")
        return ResponseEntity.ok("Location received")
    }

    @GetMapping("/restaurant/category")
    fun getFoodsByRestaurantAndCategory(
        @RequestParam restaurant: String,
        @RequestParam category: String
    ): ResponseEntity<FoodResponse> {
        val randomFoodName = randomFoodService.getFoodsByRestaurantAndCategory(restaurant, category)
        val foodResponse = FoodResponse(randomFoodName)
        return ResponseEntity.ok(foodResponse)
    }
}