package com.backend.usermicroservice

import com.backend.usermicroservice.controllers.RegistrationRequest
import com.backend.usermicroservice.models.User
import com.backend.usermicroservice.repositories.UserRepository
import com.backend.usermicroservice.services.CustomOAuth2UserService
import com.backend.usermicroservice.services.JwtService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder,
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    data class UserResponse(val user: User, val jwtToken: String)
    private val logger = LoggerFactory.getLogger(CustomOAuth2UserService::class.java)

    fun createUser(request: RegistrationRequest): ResponseEntity<Any> {
        val userPassword = passwordEncoder.encode(request.userPassword)
        val phoneNumber = request.phoneNumber
        val name = request.name

        val existingUser = userRepository.findByPhoneNumber(phoneNumber)
        if (existingUser != null) {
            logger.error("Error creating user: phone number already in use")
            return ResponseEntity("Phone number already in use", HttpStatus.I_AM_A_TEAPOT)
        }

        val user = User(name = name, phoneNumber = phoneNumber, userPassword = userPassword)
        userRepository.save(user)

        val notification = "User with phone number $phoneNumber has been created."
        kafkaTemplate.send("user-topic", notification)

        val jwtToken = jwtService.generateToken(phoneNumber)
        return ResponseEntity(UserResponse(user, jwtToken), HttpStatus.OK)
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

        val notification = mapOf("userId" to user.id, "message" to "Your profile has been updated.")
        kafkaTemplate.send("notification-topic", notification.toString())

        return UserResponse(user, jwtToken)
    }

    fun deleteUser(username: String): Boolean {
        val user = userRepository.findByName(username) ?: return false

        userRepository.delete(user)

        val notification = mapOf("userId" to user.id, "message" to "Your account has been deleted.")
        kafkaTemplate.send("notification-topic", notification.toString())

        return true
    }
}