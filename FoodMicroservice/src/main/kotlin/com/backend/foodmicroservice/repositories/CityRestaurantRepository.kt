package com.backend.foodmicroservice.repositories

import com.backend.foodmicroservice.models.City
import com.backend.foodmicroservice.models.CityRestaurant
import com.backend.foodmicroservice.models.Restaurant
import org.springframework.data.jpa.repository.JpaRepository

interface CityRestaurantRepository : JpaRepository<CityRestaurant, Int> {
    fun findByCity(city: City): List<CityRestaurant>
    fun findByCityAndRestaurant(city: City, restaurant: Restaurant): CityRestaurant?
    fun existsByCityAndRestaurant(city: City, restaurant: Restaurant): Boolean
}