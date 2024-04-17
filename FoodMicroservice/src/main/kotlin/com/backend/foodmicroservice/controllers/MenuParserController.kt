package com.backend.foodmicroservice.controllers

import com.backend.foodmicroservice.services.MenuParserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/parser")
class MenuParserController(private val menuParserService: MenuParserService) {

    @GetMapping("/parseRestaurant/{cityName}")
    fun parseRestaurant(@PathVariable cityName: String): ResponseEntity<String> {
        val coords = menuParserService.yandexApi(cityName)
        menuParserService.parseLayout(coords, cityName)
        return ResponseEntity.ok("Restaurant parsing done for city: $cityName")
    }

    @GetMapping("/parseMenu/{cityName}")
    fun parseMenu(@PathVariable cityName: String): ResponseEntity<String> {
        menuParserService.parseMenu(cityName)
        return ResponseEntity.ok("Menu parsing done for city: $cityName")
    }
}