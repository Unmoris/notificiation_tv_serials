package com.tinkoff.telegrambot.dto

class UserDtoResponse(
    val chat: Long,
    val apiKey: String = API_KEY_DEFAULT
)

const val API_KEY_DEFAULT = "k_aaaaaaaa"