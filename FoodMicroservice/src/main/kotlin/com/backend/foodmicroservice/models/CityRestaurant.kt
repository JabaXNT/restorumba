package com.backend.foodmicroservice.models

import jakarta.persistence.*

@Entity
@Table(name = "CityRestaurants")
data class CityRestaurant (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int,

    @ManyToOne
    @JoinColumn(name = "city_id")
    var city: City,

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    var restaurant: Restaurant
)