package com.backend.foodmicroservice.models

import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "Foods")
data class Food (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    var foodId: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    var restaurant: Restaurant,

    @Column(name = "name")
    var name: String,

    @Column(name = "category")
    var category: String,

    @Column(name = "default_price")
    var defaultPrice: Double,

    @Column(name = "weight")
    var weight: String,

    @Column(name = "description", length = 100000)
    var description: String,

    @Column(name = "image_url")
    var imageUrl: String
) : Serializable {
    override fun toString(): String {
        return "Food(foodId=$foodId, restaurantId=${restaurant.restaurantId}, name='$name', category='$category', defaultPrice=$defaultPrice, weight='$weight', description='$description', imageUrl='$imageUrl')"
    }
}