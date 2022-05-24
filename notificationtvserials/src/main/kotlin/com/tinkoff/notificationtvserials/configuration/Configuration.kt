package com.tinkoff.notificationtvserials.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class Configuration {

    @Bean
    fun webClient(builder: WebClient.Builder): WebClient = builder
        .baseUrl(URL_IMDB)
        .build()



}

const val URL_IMDB = "https://imdb-api.com/"

