package com.tinkoff.telegrambot.dto

class SerialEpisodeNotificationDto(
    val chatId: Long,
    val title: String,
    val imdbId: String,
    val titleEpisode: String,
    val season: Int,
)