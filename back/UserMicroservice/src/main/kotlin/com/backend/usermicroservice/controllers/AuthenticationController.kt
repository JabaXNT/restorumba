package com.backend.usermicroservice.controllers

import com.backend.usermicroservice.services.JwtService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class AuthenticationRequest(val phoneNumber: String, val userPassword: String)
data class AuthenticationResponse(val jwt: String)

@RestController
class AuthenticationController(
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService
) {
    @PostMapping("/authenticate")
    fun createAuthenticationToken(@RequestBody authenticationRequest: AuthenticationRequest): ResponseEntity<*> {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(authenticationRequest.phoneNumber, authenticationRequest.userPassword)
        )
        val jwt: String = jwtService.generateToken(authenticationRequest.phoneNumber)

        return ResponseEntity.ok(AuthenticationResponse(jwt))
    }
}