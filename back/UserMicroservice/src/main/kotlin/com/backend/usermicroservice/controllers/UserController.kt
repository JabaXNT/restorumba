package com.backend.usermicroservice.controllers

import com.backend.usermicroservice.UserService
import com.backend.usermicroservice.models.User
import com.backend.usermicroservice.services.CustomOAuth2UserService
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {
    private val logger = LoggerFactory.getLogger(CustomOAuth2UserService::class.java)


    @GetMapping("/me")
    fun getCurrentUser(): UserService.UserResponse? {
        val username = SecurityContextHolder.getContext().authentication.name
        logger.info(username)
        return userService.getUserByPhoneNumber(username)?.let { UserService.UserResponse(it, "") }
    }

    @PutMapping("/me")
    fun updateCurrentUser(@RequestBody request: RegistrationRequest): UserService.UserResponse? {
        val username = SecurityContextHolder.getContext().authentication.name
        logger.info(username)
        return userService.updateUser(username, request)
    }

    @DeleteMapping("/me")
    fun deleteCurrentUser(): Boolean {
        val username = SecurityContextHolder.getContext().authentication.name
        logger.info(username)
        return userService.deleteUser(username)
    }
}