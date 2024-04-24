package com.backend.usermicroservice.repositories

import com.backend.usermicroservice.models.OAuthUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OAuthUserRepository : JpaRepository<OAuthUser, Long> {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): OAuthUser?
}