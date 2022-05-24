package com.tinkoff.notificationtvserials.repository

import com.tinkoff.notificationtvserials.entity.UserEntity
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional


@Repository
interface UserRepository : CrudRepository<UserEntity, Long> {

    fun existsByChat(chat: Long): Boolean


    @Transactional
    @Modifying
    @Query("update UserEntity u set u.apiKey = ?1 where u.chat = ?2")
    fun updateApiKeyByChat(apiKey: String, chat: Long): Int


    fun findByChat(chat: Long): UserEntity

}