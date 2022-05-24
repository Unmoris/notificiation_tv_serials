package com.tinkoff.telegrambot.configuration

import org.apache.activemq.command.ActiveMQQueue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.core.JmsTemplate
import javax.jms.Queue


@Configuration
@EnableJms
class JmsConfiguration(private val jmsTemplate: JmsTemplate) {

    @Bean
    fun createQueue(): Queue {
        return ActiveMQQueue(NAME_QUEUE)
    }
}
const val NAME_QUEUE = "message_notification"