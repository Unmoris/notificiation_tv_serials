package com.tinkoff.notificationtvserials.entity

import javax.persistence.*

@Entity
@Table(name = "user_imdb")
class UserEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @Column(name = "chat_telegram")
    var chat: Long? = null,

    @Column(name = "api_key", nullable = false)
    var apiKey: String ,
)

