package com.tinkoff.notificationtvserials.client

import com.tinkoff.notificationtvserials.dto.SeasonDtoResponse
import com.tinkoff.notificationtvserials.dto.SerialsSearchDtoResponse
import com.tinkoff.notificationtvserials.dto.TitleDto
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class ImdbClient(private val webClient: WebClient) {

    fun searchSerial(apiKey: String, searchString: String): SerialsSearchDtoResponse? {
        return webClient.get()
            .uri("$LANGUAGE/API/$SEARCH_SERIES/$apiKey/$searchString")
            .retrieve()
            .bodyToMono<SerialsSearchDtoResponse>()
            .block()
    }

    fun getSeason(apiKey: String, imdbId: String, numberSeason: Int): SeasonDtoResponse? {
        return webClient.get()
            .uri("$LANGUAGE/API/$SEARCH_SEASON/$apiKey/$imdbId/$numberSeason")
            .retrieve()
            .bodyToMono<SeasonDtoResponse>()
            .block()
    }

    fun getTitle(apiKey: String, imdbId: String): TitleDto?{
        return webClient.get()
                .uri("$LANGUAGE/API/$SEARCH_TITLE/$apiKey/$imdbId")
                .retrieve()
                .bodyToMono<TitleDto>()
                .block()
    }
}

const val SEARCH_TITLE = "Title"
const val SEARCH_SEASON = "SeasonEpisodes"
const val SEARCH_SERIES: String = "SearchSeries"
const val LANGUAGE: String = "en"
//@Service
//class ImdbClient(private val webClient: WebClient) {
//
//    fun searchSerial(apiKey: String, searchString: String): SerialsSearchDtoResponse? {
//        return SerialsSearchDtoResponse(
//            listOf(
//                SerialTitleDto("tt1111111", "Lost", "I am lost"),
//                SerialTitleDto("tt0000000", "Lost in code", "I am lost in code")
//            )
//        )
//    }
//
//    fun getSeason(apiKey: String, imdbId: String, numberSeason: Int): SeasonDtoResponse? {
//        return SeasonDtoResponse(
//            "2002",
//            listOf(
//                EpisodeDtoResponse(1, "Episode 1", "23 May 2022"),
//                EpisodeDtoResponse(2, "Episode 2", "24 May 2022")
//            )
//        )
//    }
//}