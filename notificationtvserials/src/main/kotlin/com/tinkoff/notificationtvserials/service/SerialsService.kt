package com.tinkoff.notificationtvserials.service

import com.tinkoff.notificationtvserials.client.ImdbClient
import com.tinkoff.notificationtvserials.dto.SeasonDtoResponse
import com.tinkoff.notificationtvserials.dto.SerialResponseDto
import com.tinkoff.notificationtvserials.dto.SerialSeasonDtoResponse
import com.tinkoff.notificationtvserials.dto.SerialsResponseDto
import com.tinkoff.notificationtvserials.entity.SerialsEntity
import com.tinkoff.notificationtvserials.repository.SerialsRepository
import com.tinkoff.notificationtvserials.repository.UserRepository
import com.tinkoff.notificationtvserials.service.utility.FindLastDateSerial
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class SerialsService(
    private val userRepository: UserRepository,
    private val serialsRepository: SerialsRepository,
    private val imdbClient: ImdbClient,
    private val userService: UserService,
    private val findLastDateSerial: FindLastDateSerial
) {
    fun getSeason(apiKey: String, imdbId: String, numberSeason: Int): SeasonDtoResponse? {
        return imdbClient.getSeason(apiKey, imdbId, numberSeason)
    }

    fun getSeasonOnChatId(chatId: Long, imdbId: String, numberSeason: Int): SeasonDtoResponse? {
        return imdbClient.getSeason(userRepository.findByChat(chatId).apiKey, imdbId, numberSeason)
    }

    fun addSerialToUserByChatId(chatId: Long, serial: SerialSeasonDtoResponse) {
        val apiKey = userRepository.findByChat(chatId).apiKey
        val response = imdbClient.getSeason(apiKey, serial.id, serial.season)
        val title = imdbClient.getTitle(apiKey, serial.id)

        if (response != null && title != null) {

            val date = findLastDateSerial.searchLastDate(response)

            if (!serialsRepository.existsByChatIdAndImdbId(chatId, serial.id))
                serialsRepository.save(
                    SerialsEntity(
                        null,
                        serial.id,
                        title.title,
                        serial.season,
                        date?.date,
                        date?.title,
                        chatId
                    )
                )
        }
    }

    fun updateSerialToUserByChatId(chatId: Long, serial: SerialSeasonDtoResponse) {
        val response = imdbClient.getSeason(userRepository.findByChat(chatId).apiKey, serial.id, serial.season)
        if (response != null) {
            val date = findLastDateSerial.searchLastDate(response)
            if (serialsRepository.existsByChatIdAndImdbId(chatId, serial.id) && date != null) {
                serialsRepository.updateTitleEpisodeAndLastDateById(date.title,date.date, chatId)
            }
        }
    }
    @Transactional
    fun deleteSerialToUserByChatId(chatId: Long, imdbId: String) {
        println(imdbId)
        if (serialsRepository.existsByChatIdAndImdbId(chatId, imdbId)) {
            println(imdbId)
            serialsRepository.deleteByChatIdAndImdbId(chatId, imdbId)
        }
    }

    fun getAllSerialsByChatId(chatId: Long): SerialsResponseDto {
        return SerialsResponseDto(
            serials = serialsRepository.findByChatId(chatId)
                .map { SerialResponseDto(it.imdbId, it.title, it.season, it.lastDate) })
    }
}

