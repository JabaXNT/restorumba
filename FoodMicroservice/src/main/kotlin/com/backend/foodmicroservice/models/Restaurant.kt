package com.backend.foodmicroservice.models

import jakarta.persistence.*
import java.io.Serializable
import java.time.LocalDateTime

@Entity
@Table(name = "Restaurants")
data class Restaurant (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
        val restaurantId: Long,

    @Column(name = "name", unique = true)
        val name: String,

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    var cityRestaurants: List<CityRestaurant> = mutableListOf(),

    @Column(name = "last_updated")
        val lastUpdated: LocalDateTime,

    @OneToMany(mappedBy = "restaurant", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
        var foods: MutableList<Food> = mutableListOf(),

    @Column(name = "slug")
    var slug: String
) : Serializable {
    override fun toString(): String {
        return "Restaurant(restaurantId=$restaurantId, name='$name', lastUpdated=$lastUpdated, slug='$slug')"
    }
}