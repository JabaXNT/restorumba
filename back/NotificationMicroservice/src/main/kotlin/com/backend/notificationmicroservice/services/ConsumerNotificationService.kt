package com.backend.notificationmicroservice.services

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class ConsumerNotificationService {

    @KafkaListener(topics = ["user-topic"], groupId = "group_id")
    fun consume(message: ConsumerRecord<String, String>) {
        println("Consumed message: ${message.value()}")
    }
}