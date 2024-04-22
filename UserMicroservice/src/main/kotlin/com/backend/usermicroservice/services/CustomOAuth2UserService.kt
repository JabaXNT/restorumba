package com.backend.usermicroservice.services

import com.backend.usermicroservice.UserService
import com.backend.usermicroservice.controllers.RegistrationRequest
import com.backend.usermicroservice.models.OAuthUser
import com.backend.usermicroservice.repositories.OAuthUserRepository
import org.slf4j.LoggerFactory
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CustomOAuth2UserService(
    private val oAuthUserRepository: OAuthUserRepository,
) : DefaultOAuth2UserService() {

    private val logger = LoggerFactory.getLogger(CustomOAuth2UserService::class.java)

    override fun loadUser(oAuth2UserRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(oAuth2UserRequest)

        val attributes = oAuth2User.attributes

        return DefaultOAuth2User(
            oAuth2User.authorities,
            oAuth2User.attributes,
            "name"
        )
    }

    fun processOAuthPostLogin(name: String, email: String) {
        val oAuthUser = oAuthUserRepository.findByEmail(email)
        if (oAuthUser == null) {
            oAuthUserRepository.save(OAuthUser(name = name, email = email))
            logger.info("Created new OAuth user with email: $email")
        } else {
            oAuthUser.lastLoginTime = LocalDateTime.now()
            oAuthUserRepository.save(oAuthUser)
            logger.info("User with email: $email logged in")
        }
    }
}
