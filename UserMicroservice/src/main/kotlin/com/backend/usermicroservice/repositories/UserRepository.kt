package com.backend.usermicroservice.repositories

import com.backend.usermicroservice.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByPhoneNumber(phoneNumber: String): User?
    fun findByName(name: String): User?
    fun existsByPhoneNumber(phoneNumber: String): Boolean
}