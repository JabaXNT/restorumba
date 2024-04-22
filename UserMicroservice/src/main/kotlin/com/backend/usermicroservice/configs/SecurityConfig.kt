package com.backend.usermicroservice.configs

import OAuth2LoginSuccessHandler
import com.backend.usermicroservice.services.CustomOAuth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(private val jwtAuthenticationFilter: JwtAuthenticationFilter,
                     private var authenticationProvider: AuthenticationProvider,
                     private val customOAuth2UserService: CustomOAuth2UserService
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    @Bean
    fun securityFilterChain(http: HttpSecurity): DefaultSecurityFilterChain? {
        val oAuth2LoginSuccessHandler = OAuth2LoginSuccessHandler(customOAuth2UserService)


        http
            .csrf{it.disable()}
            .authorizeHttpRequests{ it.anyRequest().permitAll() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .formLogin{ it.disable()}
            .oauth2Login { it ->
                it.userInfoEndpoint { it.userService(customOAuth2UserService) }
                it.successHandler(oAuth2LoginSuccessHandler)
            }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}