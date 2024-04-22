package com.backend.usermicroservice.controllers

import com.backend.usermicroservice.UserService
import com.backend.usermicroservice.models.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {
    @GetMapping("/me")
    fun getCurrentUser(): UserService.UserResponse? {
        val username = SecurityContextHolder.getContext().authentication.name
        return userService.getUserByUsername(username)?.let { UserService.UserResponse(it, "") }
    }

    @PutMapping("/me")
    fun updateCurrentUser(@RequestBody request: RegistrationRequest): UserService.UserResponse? {
        val username = SecurityContextHolder.getContext().authentication.name
        return userService.updateUser(username, request)
    }

    @DeleteMapping("/me")
    fun deleteCurrentUser(): Boolean {
        val username = SecurityContextHolder.getContext().authentication.name
        return userService.deleteUser(username)
    }
}