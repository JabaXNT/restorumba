package com.backend.foodmicroservice.models

import jakarta.persistence.*

@Entity
@Table(name = "Cities")
data class City (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
        val cityId: Int,

    @Column(name = "name", unique = true)
        val name: String,

    @OneToMany(mappedBy = "city", fetch = FetchType.EAGER, orphanRemoval = true)
    var cityRestaurants: List<CityRestaurant> = mutableListOf()
) {
    override fun toString(): String {
        return "City(cityId=$cityId, name='$name')"
    }
}