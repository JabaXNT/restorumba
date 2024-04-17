package com.backend.foodmicroservice.repositories

import com.backend.foodmicroservice.models.City
import com.backend.foodmicroservice.models.Food
import com.backend.foodmicroservice.models.Restaurant
import org.springframework.data.jpa.repository.JpaRepository

interface RestaurantRepository : JpaRepository<Restaurant, Int> {
    fun findByName(name: String): Restaurant?
    fun findBySlug(slug: String): Restaurant?
    fun existsByName(name: String): Boolean
}