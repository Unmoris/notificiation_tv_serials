package com.tinkoff.notificationtvserials.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "serials")
class SerialsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @Column(name = "imdb_id")
    var imdbId: String? = null,

    @Column(name = "title")
    var title: String? = null,

    @Column(name = "season")
    var season: Int? = null,

    @Column(name = "last_date")
    var lastDate: Date? = null,

    @Column(name = "title_episode")
    var titleEpisode: String? = null,

    @Column(name = "chat_id")
    var chatId: Long? = null,

    )