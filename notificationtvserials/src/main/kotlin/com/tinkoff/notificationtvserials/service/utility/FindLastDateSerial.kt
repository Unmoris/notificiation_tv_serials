package com.tinkoff.notificationtvserials.service.utility

import com.tinkoff.notificationtvserials.dto.EpisodeTitleDateDto
import com.tinkoff.notificationtvserials.dto.SeasonDtoResponse
import org.springframework.stereotype.Service
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*


@Service
class FindLastDateSerial {
    val mouth =
        arrayOf("Jan.", "Feb.", "Mar.", "Apr.", "May", "Jun.", "Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec.")
    val formatter = SimpleDateFormat("dd MMM yyyy")

    fun searchLastDate(season: SeasonDtoResponse): EpisodeTitleDateDto {


        val sym = DateFormatSymbols()
        sym.months = mouth
        formatter.dateFormatSymbols = sym

        //Время которе сейчас
        val date = Calendar.getInstance().time

        // Если дата была до сегодняшнего дня, то берём первую
        var lastEpisode = season.episodes.find {
            date > formatter.parse(it.released.lowercase())
        }?.let { EpisodeTitleDateDto(it.title, formatter.parse(it.released.lowercase())) }

        if (lastEpisode == null)
            lastEpisode =
                season.episodes
                    .last()
                    .let { EpisodeTitleDateDto(it.title, formatter.parse(it.released.lowercase())) }
        return lastEpisode
    }
}