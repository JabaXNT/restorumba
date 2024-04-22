package com.backend.usermicroservice.services

import com.backend.usermicroservice.controllers.RegistrationRequest
import com.backend.usermicroservice.models.Role
import com.backend.usermicroservice.models.User
import com.backend.usermicroservice.repositories.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AdminService(private val userRepository: UserRepository,
                   private val passwordEncoder: PasswordEncoder
) {

    fun createAdmin(request: RegistrationRequest): User {
        val userPassword = passwordEncoder.encode(request.userPassword)
        val phoneNumber = request.phoneNumber
        val name = request.name

        val user = User(name = name, phoneNumber = phoneNumber, userPassword = userPassword, role = Role.ADMIN)
        userRepository.save(user)

        return user
    }

    fun adminExists(): Boolean {
        return userRepository.findByName("admin") != null
    }

    fun findUserByUsername(username: String): User? {
        return userRepository.findByName(username)
    }

    fun findUserByPhoneNumber(phoneNumber: String): User? {
        return userRepository.findByPhoneNumber(phoneNumber)
    }

    fun deleteUser(username: String): Boolean {
        val user = userRepository.findByName(username) ?: return false
        userRepository.delete(user)
        return true
    }
}