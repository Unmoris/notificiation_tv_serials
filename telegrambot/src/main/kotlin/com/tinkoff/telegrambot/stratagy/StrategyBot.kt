package com.tinkoff.telegrambot.stratagy

import com.elbekD.bot.feature.chain.chain
import com.elbekD.bot.feature.chain.jumpToAndFire
import com.tinkoff.telegrambot.client.MESSAGE_ERROR
import com.tinkoff.telegrambot.client.NotificationClient
import com.tinkoff.telegrambot.configuration.bot
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StrategyBot(private val notificationClient: NotificationClient) {

    @Bean
    fun startCommand() {
        bot.onCommand(COMMAND_START) { msg, _ ->
            bot.sendMessage(msg.chat.id, MESSAGE_START)
            notificationClient.addUser(msg.chat.id)?.let {
                bot.sendMessage(msg.chat.id, it)
            }
        }
    }

    @Bean
    fun updateApiKeyUser() {
        bot.chain(
            label = "update",
            predicate = { msg -> msg.text.equals(COMMAND_UPDATE) },
            action = { msg ->
                bot.sendMessage(msg.chat.id, MESSAGE_INPUT_API_KEY)
            }
        ).then("input_api_key") { msg ->
            if (msg.text != null)
                bot.sendMessage(msg.chat.id, notificationClient.updateUser(msg.chat.id, msg.text!!)!!)
            else
                bot.sendMessage(msg.chat.id, MESSAGE_INPUT_ERROR)
            bot.jumpToAndFire("exit", msg)
        }.then("exit", isTerminal = true) { msg ->
            bot.sendMessage(msg.chat.id, MESSAGE_UPDATE_EXIT)
        }.build()
    }

    @Bean
    fun getUserSerials() {
        bot.onCommand(COMMAND_VIEW) { msg, _ ->
            bot.sendMessage(msg.chat.id, MESSAGE_VIEW)
            notificationClient.getUserSerialsByChatId(msg.chat.id)?.serials?.forEach {
                bot.sendMessage(msg.chat.id, viewIfo(it.title, it.imdbId, it.season))
            }
        }
    }

    @Bean
    fun deleteUserSerials() {
        bot.chain(
            label = "delete",
            predicate = { msg -> msg.text.equals(COMMAND_DELETE) },
            action = { msg ->
                bot.sendMessage(msg.chat.id, MESSAGE_VIEW)
                notificationClient.getUserSerialsByChatId(msg.chat.id)?.serials?.forEach {
                    bot.sendMessage(msg.chat.id, viewIfo(it.title, it.imdbId, it.season))
                }
            }
        ).then("click_id") { msgImdbId ->
            if (msgImdbId.text != null)
                if (msgImdbId.text!!.contains("/tt")) bot.jumpToAndFire("delete_serial", msgImdbId)
                else {
                    bot.jumpToAndFire("exit", msgImdbId)
                }
        }.then("delete_serial") { msgImdbId ->
            try {
                notificationClient.deleteSerial(
                    msgImdbId.chat.id,
                    clickImdbId(msgImdbId.text!!)
                )?.let { bot.sendMessage(msgImdbId.chat.id, it) }
            } catch (e: RuntimeException) {
                bot.sendMessage(msgImdbId.chat.id, MESSAGE_ERROR)
            }
            bot.jumpToAndFire("exit", msgImdbId)
        }.then("exit", isTerminal = true) { msg -> bot.sendMessage(msg.chat.id, MESSAGE_DELETE_EXIT) }
            .build()
    }

    @Bean
    fun addSerial() {
        bot.chain(
            label = "add_serial",
            predicate = { msg -> msg.text.equals(COMMAND_ADD) },
            action = { msg -> bot.sendMessage(msg.chat.id, MESSAGE_INPUT_API_KEY) }
        ).then { msgImdbId ->
            if (msgImdbId.text != null)
                if (msgImdbId.text!!.contains("/tt")) bot.jumpToAndFire("add_serial", msgImdbId)
                else {
                    bot.jumpToAndFire("exit", msgImdbId)
                }
        }.then("add_serial", isTerminal = true) { msgImdbId ->
            bot.sendMessage(msgImdbId.chat.id, MESSAGE_INPUT_SEASON)
            bot.onMessage { numberSeason ->
                if (numberSeason.text != null) {
                    try {
                        notificationClient.addSerial(
                            msgImdbId.chat.id,
                            clickImdbId(msgImdbId.text!!),
                            numberSeason.text!!.toInt()
                        )?.let { bot.sendMessage(msgImdbId.chat.id, it) }
                    } catch (e: RuntimeException) {
                        bot.sendMessage(msgImdbId.chat.id, MESSAGE_ERROR)
                    }
                }
            }
        }.then("exit", isTerminal = true) { msg -> bot.sendMessage(msg.chat.id, MESSAGE_ADD_EXIT) }
            .build()
    }


    @Bean
    fun searchSerial() {
        bot.chain(
            label = "search",
            predicate = { msg -> msg.text.equals(COMMAND_SEARCH) },
            action = { msg -> bot.sendMessage(msg.chat.id, MESSAGE_INPUT_SEARCH) }
        ).then("searchString") { msg ->
            if (msg.text != null) {
                notificationClient.searchSerial(msg.chat.id, msg.text!!)?.results?.forEach {
                    bot.sendMessage(
                        msg.chat.id,
                        searchIfo(it.title, it.description, it.id)
                    )
                }
                bot.jumpToAndFire("exit_search", msg)
            }
        }.then("exit_search", isTerminal = true) { msg_exit -> bot.sendMessage(msg_exit.chat.id, MESSAGE_SEARCH_EXIT) }
            .build()
    }
}