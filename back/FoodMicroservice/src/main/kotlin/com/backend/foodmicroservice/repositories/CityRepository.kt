package com.backend.foodmicroservice.repositories

import org.springframework.data.jpa.repository.JpaRepository
import com.backend.foodmicroservice.models.City

interface CityRepository : JpaRepository<City, Int> {
    fun findByName(name: String): City?
}