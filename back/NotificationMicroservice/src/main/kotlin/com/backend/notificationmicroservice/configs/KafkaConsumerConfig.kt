package com.backend.notificationmicroservice.configs

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory

@Configuration
class KafkaConsumerConfig {

    @Value("\${spring.kafka.producer.bootstrap-servers}")
    private lateinit var bootstrapServers: String

    @Bean
    fun consumerFactory(): ConsumerFactory<String, String> {
        val configProps: MutableMap<String, Any> = HashMap()
        configProps[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        configProps[ConsumerConfig.GROUP_ID_CONFIG] = "group_id"
        configProps[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        configProps[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        return DefaultKafkaConsumerFactory(configProps)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = consumerFactory()
        return factory
    }
}