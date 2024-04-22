package com.backend.usermicroservice.controllers

import com.backend.usermicroservice.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class RegistrationRequest(val phoneNumber: String, val userPassword: String, val name: String)

@RestController
class RegistrationController(
    private val userService: UserService
) {
    @PostMapping("/register")
    fun register(@RequestBody registrationRequest: RegistrationRequest): ResponseEntity<UserService.UserResponse> {
        val userResponse = userService.createUser(registrationRequest)
        return ResponseEntity.ok(userResponse)
    }
}