package com.example.taboada.rtdbexample3.model.data

import com.example.taboada.rtdbexample3.model.Message

fun getConversationMessages(): List<Message> {
    return listOf<Message>(
        Message(
            content = "Hello World!",
            timestamp = 1662847883,
            flag = "A",
            userId = "USER_ID_A"
        ),
        Message(
            content = "Hello World!",
            timestamp = 1662847960,
            flag = "A",
            userId = "USER_ID_B"
        ),
        Message(
            content = "Hello World!",
            timestamp = 1662847963,
            flag = "A",
            userId = "ME"
        ),
        Message(
            content = "Hello World!",
            timestamp = 1662847983,
            flag = "A",
            userId = "USER_ID_C"
        ),
        Message(
            content = "Hello World!",
            timestamp = 1662847990,
            flag = "A",
            userId = "ME"
        ),
        Message(
            content = "Hello World!",
            timestamp = 1662847886,
            flag = "A",
            userId = "ME"
        ),
    )
}