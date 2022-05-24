package com.tinkoff.telegrambot.client

import com.tinkoff.notificationtvserials.dto.response.SeasonDtoResponse
import com.tinkoff.notificationtvserials.dto.response.SerialsResponseDto
import com.tinkoff.telegrambot.dto.SerialSeasonDtoResponse
import com.tinkoff.telegrambot.dto.SerialsSearchDtoResponse
import com.tinkoff.telegrambot.dto.UserDtoResponse
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class NotificationClient(private val webClient: WebClient) {

    fun searchSerial(chatId: Long, searchString: String): SerialsSearchDtoResponse? {
        return webClient.get()
            .uri("$CHAT_TELEGRAM/$chatId/$SEARCH_SERIES/?searchString=$searchString")
            .retrieve()
            .bodyToMono<SerialsSearchDtoResponse>()
            .block()
    }

    fun addUser(chatId: Long): String? {
        return webClient.post()
            .uri("/user/add")
            .body(BodyInserters.fromValue(UserDtoResponse(chatId)))
            .retrieve()
            .bodyToMono<String>()
            .block()
    }

    fun updateUser(chatId: Long, apiKey: String): String? {
        return webClient.post()
            .uri("/user/update")
            .body(BodyInserters.fromValue(UserDtoResponse(chatId, apiKey)))
            .retrieve()
            .bodyToMono<String>()
            .block()
    }

    fun getSeason(chatId: Long, imdbId: String, numberSeason: String): SeasonDtoResponse? {
        return webClient.get()
            .uri("$CHAT_TELEGRAM/$chatId/$SEARCH_SEASON/$imdbId/$numberSeason")
            .retrieve()
            .bodyToMono<SeasonDtoResponse>()
            .block()
    }

    fun getUserSerialsByChatId(chatId: Long): SerialsResponseDto? {
        return webClient.get()
            .uri("$CHAT_TELEGRAM/$chatId")
            .retrieve()
            .bodyToMono<SerialsResponseDto>()
            .block()
    }


    fun addSerial(chatId: Long, imdbId: String, numberSeason: Int): String? {
        return webClient.post()
            .uri("$CHAT_TELEGRAM/$chatId")
            .body(BodyInserters.fromValue(SerialSeasonDtoResponse(imdbId, numberSeason)))
            .retrieve()
            .bodyToMono<String>()
            .block()
    }

    fun deleteSerial(chatId: Long, imdbId: String): String? {
        return webClient.delete()
            .uri("$CHAT_TELEGRAM/$chatId/$imdbId")
            .retrieve()
            .bodyToMono<String>()
            .block()
    }

}

const val CHAT_TELEGRAM = "chat-telegram"
const val SEARCH_SEASON = "get-season"
const val SEARCH_SERIES: String = "search-serial"

