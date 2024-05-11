package com.backend.foodmicroservice.services

import com.backend.foodmicroservice.repositories.CityRepository
import com.backend.foodmicroservice.repositories.FoodRepository
import com.backend.foodmicroservice.repositories.RestaurantRepository
import org.springframework.stereotype.Service

@Service
class UtilityInformationService(
    private val cityRepo: CityRepository,
    private val foodRepo: FoodRepository,
    private val restRepo: RestaurantRepository
) {

}