package com.backend.foodmicroservice.controllers

import com.backend.foodmicroservice.services.RandomFoodService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/restorumba")
class RandomFoodController(private val randomFoodService: RandomFoodService) {

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
}