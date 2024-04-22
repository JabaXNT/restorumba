package com.backend.foodmicroservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling


@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = ["com.backend.foodmicroservice.repositories"])
class FoodMicroserviceApplication

fun main(args: Array<String>) {
    runApplication<FoodMicroserviceApplication>(*args)
}
