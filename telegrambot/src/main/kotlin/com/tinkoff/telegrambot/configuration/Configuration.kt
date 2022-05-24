package com.tinkoff.telegrambot.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class Configuration {

    @Bean
    fun webClient(builder: WebClient.Builder): WebClient= builder.baseUrl("http://$HOST_NOTIFICATION").build()

}

const val HOST_NOTIFICATION = "localhost:8081"
