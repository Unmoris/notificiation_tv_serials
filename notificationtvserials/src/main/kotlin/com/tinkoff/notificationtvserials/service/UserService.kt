package com.tinkoff.notificationtvserials.service

import com.tinkoff.notificationtvserials.client.ImdbClient
import com.tinkoff.notificationtvserials.dto.SerialsSearchDtoResponse
import com.tinkoff.notificationtvserials.dto.UserDtoResponse
import com.tinkoff.notificationtvserials.entity.UserEntity
import com.tinkoff.notificationtvserials.exception.AlreadyExistsException
import com.tinkoff.notificationtvserials.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val repository: UserRepository, private val imdbClient: ImdbClient) {

    fun addUser(user: UserDtoResponse) {
        if (!repository.existsByChat(user.chat))
            repository.save(UserEntity(chat = user.chat, apiKey = user.apiKey))
        else throw AlreadyExistsException("User already exists")
    }

    fun updateUserApi(user: UserDtoResponse) {
        if (repository.existsByChat(user.chat))
            repository.updateApiKeyByChat(user.apiKey, user.chat)
    }

    fun searchSerial(userId: Long, searchString: String): SerialsSearchDtoResponse? {
        val apiKey = repository.findById(userId).get().apiKey

        return imdbClient.searchSerial(apiKey, searchString)
    }

    fun searchSerialOnChatId(ChatId: Long, searchString: String): SerialsSearchDtoResponse? {
        val apiKey = repository.findByChat(ChatId).apiKey
        println(ChatId)
        println(apiKey)
        println(searchString)
        return imdbClient.searchSerial(apiKey, searchString)
    }
}