package com.tinkoff.notificationtvserials.repository

import com.tinkoff.notificationtvserials.entity.SerialsEntity
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface SerialsRepository :CrudRepository<SerialsEntity,Long> {

    fun existsByChatIdAndImdbId(chatId: Long, imdbId: String): Boolean

    fun deleteByChatIdAndImdbId(chatId: Long, imdbId: String): Long

    fun findByChatId(chatId: Long): List<SerialsEntity>

    fun findByLastDateIsLessThanEqual(lastDate: Date): List<SerialsEntity>

    @Transactional
    @Modifying
    @Query("update SerialsEntity s set s.titleEpisode = ?1, s.lastDate = ?2 where s.id = ?3")
    fun updateTitleEpisodeAndLastDateById(titleEpisode: String, lastDate: Date, id: Long): Int

}