package com.tinkoff.telegrambot.listner

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.tinkoff.telegrambot.configuration.NAME_QUEUE
import com.tinkoff.telegrambot.configuration.bot
import com.tinkoff.telegrambot.dto.SerialEpisodeNotificationDto
import com.tinkoff.telegrambot.stratagy.remindSerial
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component

@Component
class ListenerJms(private val mapper: ObjectMapper = jacksonObjectMapper()) {

    @JmsListener(destination = NAME_QUEUE)
    fun listener(serial: String) {
        val serial = mapper.readValue(serial, SerialEpisodeNotificationDto::class.java)
        bot.sendMessage(serial.chatId, remindSerial(serial.title, serial.imdbId, serial.season, serial.titleEpisode))
    }
}