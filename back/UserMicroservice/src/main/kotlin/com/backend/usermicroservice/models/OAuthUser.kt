package com.backend.usermicroservice.models

import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
@Table(name="OAuthUsers")
data class OAuthUser(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    @Column(name="username")
    val name: String = "BOB",
    @Column(name="email", unique = true)
    val email: String = "example@gmail.com",
    @Column(name="session_token")
    var sessionToken: String = "",
    @Column(name="last_login_time")
    var lastLoginTime: LocalDateTime = LocalDateTime.now()
)