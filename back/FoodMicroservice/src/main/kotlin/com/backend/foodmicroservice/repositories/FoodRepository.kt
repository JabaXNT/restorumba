package com.backend.foodmicroservice.repositories

import com.backend.foodmicroservice.models.Food
import com.backend.foodmicroservice.models.Restaurant
import org.springframework.context.annotation.Description
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface FoodRepository : JpaRepository<Food, Int> {

    fun findAllByRestaurant_RestaurantId(restaurantId: Long): List<Food>

    @Query("SELECT f FROM Food f WHERE f.restaurant.restaurantId = (SELECT r.restaurantId FROM Restaurant r WHERE r.name = :restaurantName) AND f.category = :category")
    fun findAllByRestaurantNameAndCategory(@Param("restaurantName") restaurantName: String, @Param("category") category: String): List<Food>

    fun existsByNameAndRestaurantAndCategoryAndDescriptionAndDefaultPriceAndImageUrl(
        name: String,
        restaurant: Restaurant,
        category: String,
        description: String,
        defaultPrice: Double,
        imageUrl: String
    ): Boolean
}