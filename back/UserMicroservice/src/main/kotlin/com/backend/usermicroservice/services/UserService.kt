package com.backend.usermicroservice

import com.backend.usermicroservice.controllers.RegistrationRequest
import com.backend.usermicroservice.models.User
import com.backend.usermicroservice.repositories.UserRepository
import com.backend.usermicroservice.services.CustomOAuth2UserService
import com.backend.usermicroservice.services.JwtService
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder
) {
    data class UserResponse(val user: User, val jwtToken: String)
    private val logger = LoggerFactory.getLogger(CustomOAuth2UserService::class.java)

    fun createUser(request: RegistrationRequest): UserResponse {
        val userPassword = passwordEncoder.encode(request.userPassword)
        val phoneNumber = request.phoneNumber
        val name = request.name

        val user =
            User(name = name, phoneNumber = phoneNumber, userPassword = userPassword)
        userRepository.save(user)

        val jwtToken = jwtService.generateToken(phoneNumber)
        return UserResponse(user, jwtToken)
    }

    fun getUserByUsername(username: String): User? {
        return userRepository.findByName(username)
    }

    fun updateUser(username: String, request: RegistrationRequest): UserResponse? {
        val user = userRepository.findByName(username) ?: return null

        user.name = request.name
        user.phoneNumber = request.phoneNumber
        user.userPassword = passwordEncoder.encode(request.userPassword)

        userRepository.save(user)

        val jwtToken = jwtService.generateToken(user.phoneNumber)
        return UserResponse(user, jwtToken)
    }

    fun deleteUser(username: String): Boolean {
        val user = userRepository.findByName(username) ?: return false

        userRepository.delete(user)
        return true
    }
}