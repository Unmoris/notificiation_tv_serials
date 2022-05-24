package com.tinkoff.notificationtvserials.controller

import com.tinkoff.notificationtvserials.dto.SerialSeasonDtoResponse
import com.tinkoff.notificationtvserials.service.SerialsService
import com.tinkoff.notificationtvserials.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/chat-telegram")
class TelegramChatController(private val userService: UserService, private val serialsService: SerialsService) {

    @GetMapping("{chatId}/search-serial", params = ["searchString"])
    fun searchSerial(@PathVariable chatId: String, @RequestParam searchString: String): ResponseEntity<Any> {
        return try {
            val temp = userService.searchSerialOnChatId(chatId.toLong(), searchString)
            println(temp==null)
            if(temp != null){
                println(temp.results==null)
            }
            ResponseEntity(temp, HttpStatus.CREATED)
        } catch (e: RuntimeException) {
            e.printStackTrace()
            ResponseEntity(MESSAGE_ERROR, HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("{chatId}")
    fun addSerial(@PathVariable chatId: String, @RequestBody serial: SerialSeasonDtoResponse): ResponseEntity<Any> {
        return try {
            serialsService.addSerialToUserByChatId(chatId.toLong(), serial)
            ResponseEntity(MESSAGE_SERIAL_ADD, HttpStatus.CREATED)
        } catch (e: RuntimeException) {
            e.printStackTrace()
            ResponseEntity(MESSAGE_ERROR, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("{chatId}/{imdbId}")
    fun deleteSerial(@PathVariable chatId: String, @PathVariable imdbId: String): ResponseEntity<Any> {
        return try {
            serialsService.deleteSerialToUserByChatId(chatId.toLong(), imdbId)
            ResponseEntity(MESSAGE_SERIAL_DELETED, HttpStatus.OK)
        } catch (e: RuntimeException) {
            e.printStackTrace()
            ResponseEntity(MESSAGE_ERROR, HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("{chatId}/get-season/{imdbId}/{numberSeason}")
    fun getSeason(
        @PathVariable chatId: String,
        @PathVariable imdbId: String,
        @PathVariable numberSeason: String
    ): ResponseEntity<Any> {
        return try {
            ResponseEntity(
                serialsService.getSeasonOnChatId(chatId.toLong(), imdbId, numberSeason.toInt()),
                HttpStatus.OK
            )
        } catch (e: RuntimeException) {
            e.printStackTrace()
            ResponseEntity(MESSAGE_ERROR, HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("{chatId}")
    fun getSerialsByChatId(
        @PathVariable chatId: String,
    ): ResponseEntity<Any> {
        return try {
            ResponseEntity(
                serialsService.getAllSerialsByChatId(chatId.toLong()), // Лишняя херня
                HttpStatus.OK
            )
        } catch (e: RuntimeException) {
            e.printStackTrace()
            ResponseEntity(MESSAGE_ERROR, HttpStatus.BAD_REQUEST)
        }
    }

}