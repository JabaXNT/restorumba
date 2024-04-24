package com.backend.foodmicroservice.repositories

import com.backend.foodmicroservice.models.Food
import com.backend.foodmicroservice.models.Restaurant
import org.springframework.context.annotation.Description
import org.springframework.data.jpa.repository.JpaRepository

interface FoodRepository : JpaRepository<Food, Int> {

    fun findAllByRestaurant_RestaurantId(restaurantId: Long): List<Food>

    fun existsByNameAndRestaurantAndCategoryAndDescriptionAndDefaultPriceAndImageUrl(
        name: String,
        restaurant: Restaurant,
        category: String,
        description: String,
        defaultPrice: Double,
        imageUrl: String
    ): Boolean
}