package com.tinkoff.notificationtvserials.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.tinkoff.notificationtvserials.configuration.NAME_QUEUE
import com.tinkoff.notificationtvserials.dto.SerialEpisodeNotificationDto
import com.tinkoff.notificationtvserials.dto.SerialSeasonDtoResponse
import com.tinkoff.notificationtvserials.repository.SerialsRepository
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class SerialsProducer(
    private val jmsTemplate: JmsTemplate = JmsTemplate(),
    private val serialsRepository: SerialsRepository,
    private val serialsService: SerialsService,
) {

    fun produceSerials() {
        val objectMapper = jacksonObjectMapper()
        val date = Calendar.getInstance().time
        val serials = serialsRepository.findByLastDateIsLessThanEqual(date)
            .filter { (it.lastDate != null) && (it.id != null) && (it.chatId != null) }
            .map { SerialEpisodeNotificationDto(it.chatId!!, it.title!!, it.imdbId!!,it.titleEpisode!!, it.season!!) }

        serials.forEach { println(objectMapper.writeValueAsString(it)) }

        for (item in serials) {

            jmsTemplate.convertAndSend(
                NAME_QUEUE,
                objectMapper.writeValueAsString(item)
            )

            serialsService.updateSerialToUserByChatId(
                item.chatId,
                SerialSeasonDtoResponse(item.imdbId!!, item.season!!)
            )
        }
    }
}