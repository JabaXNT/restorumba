package com.backend.usermicroservice.services

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService {

    private val secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun generateToken(phoneNumber: String): String {
        return Jwts.builder()
            .setSubject(phoneNumber)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 3600000)) // 1 hour
            .signWith(secretKey)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
        return true
    }

    fun getPhoneNumberFromToken(token: String): String {
        val claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body
        return claims.subject
    }
}