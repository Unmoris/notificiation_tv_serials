package com.tinkoff.telegrambot.stratagy

const val TITLE = "Заголовок"
const val DESCRIPTION = "Описание"
const val IMDB_ID = "imdbId"
const val SEASON = "Сезон"
const val EPISODE = "Серия"

fun searchIfo(title: String, description: String, id: String) =
    "$TITLE: ${title}\n$DESCRIPTION: ${description}\n$IMDB_ID = /$id"

fun viewIfo(title: String, id: String, season: Int) =
    "$TITLE: $title\n$IMDB_ID : /$id\n$SEASON : $season"

fun remindSerial(title: String, id: String, season: Int, titleEpisode:String) =
    "$TITLE: $title\n$IMDB_ID : /$id\n$SEASON : $season\n$EPISODE : $season"


fun clickImdbId(imdbId: String) = imdbId.filter { it.isDigit() or (it == 't') }
fun printImdbId(imdbId: String) = "/$imdbId"