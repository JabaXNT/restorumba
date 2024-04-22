package com.backend.usermicroservice.configs

import com.backend.usermicroservice.controllers.RegistrationRequest
import com.backend.usermicroservice.services.AdminService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StartUpConfig(private val adminService: AdminService) {

    @Value("\${admin.username}")
    private lateinit var adminUsername: String

    @Value("\${admin.phone}")
    private lateinit var adminPhone: String

    @Value("\${admin.password}")
    private lateinit var adminPassword: String

    @Bean
    fun runner() = CommandLineRunner {
        if (!adminService.adminExists()) {
            val adminRequest = RegistrationRequest(
                name = adminUsername,
                phoneNumber = adminPhone,
                userPassword = adminPassword
            )
            adminService.createAdmin(adminRequest)
        }
    }
}