package com.backend.foodmicroservice.configs

import com.backend.foodmicroservice.models.Food
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@Configuration
class RedisConfig {

    @Bean
    fun redisTemplate(cf: RedisConnectionFactory): RedisTemplate<String, List<Food>> {
        val template = RedisTemplate<String, List<Food>>()
        template.connectionFactory = cf
        return template
    }
}