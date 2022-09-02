package com.example.taboada.rtdbexample3.model

import java.util.*

data class Message(
    val id: String = UUID.randomUUID().toString(),
    val content: String = "",
    val priority: String = "",
    val flag: String = "",
    val userId: String = ""
)
