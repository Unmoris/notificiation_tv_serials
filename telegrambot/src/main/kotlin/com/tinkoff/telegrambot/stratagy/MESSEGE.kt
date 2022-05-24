package com.tinkoff.telegrambot.stratagy


const val MESSAGE_START = "Привет!\nЭто бот для напоминания выхода серий сериалов\n" +
        "Он использует открытое rest api imdb-api.com, что позволяет бесплатно делать 100 запросов в день.\n" +
        "У каждого пользователя по-умолчанию стоит общий ключ api, но вы можете зарегистрироваться на сайте" +
        " imdb-api.com и внести свой api ключ!"

const val MESSAGE_INPUT_API_KEY = "Введите apikey imdb api"
const val MESSAGE_INPUT_ERROR = "Ошибка ввода"
const val MESSAGE_INPUT_SEASON = "Введите номер сезона"
const val MESSAGE_INPUT_SEARCH = "Введите название сериала"
const val MESSAGE_SEARCH_EXIT = "Поиск закончен"
const val MESSAGE_ADD_EXIT = "Добавление закончено"
const val MESSAGE_UPDATE_EXIT = "Обновление завершено"
const val MESSAGE_VIEW = "Вот ваши сериалы:"
const val MESSAGE_DELETE_EXIT = "Удаление завершено"