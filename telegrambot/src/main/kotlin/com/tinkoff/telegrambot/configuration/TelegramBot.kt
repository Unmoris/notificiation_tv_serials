package com.tinkoff.telegrambot.configuration

import com.elbekD.bot.Bot
import com.elbekD.bot.feature.chain.chain
import com.elbekD.bot.feature.chain.jumpToAndFire
import com.tinkoff.telegrambot.client.NotificationClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private const val username: String = "BOT"
private const val token: String = "TOKEN"
val bot = Bot.createPolling(username, token)

@Configuration
class TelegramBot(
    private val notificationClient: NotificationClient,
) {
    @Bean
    fun runBot() = bot.start()

}