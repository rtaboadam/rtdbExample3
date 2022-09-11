package com.example.taboada.rtdbexample3.model

import java.sql.Timestamp
import java.util.*
import com.example.taboada.rtdbexample3.R.drawable as AppIcon

data class Message(
    val id: String = UUID.randomUUID().toString(),
    val content: String = "",
    val priority: String = "",
    val timestamp: Int = 0,
    val flag: String = "",
    val userId: String = "",
    val authorImage: Int = if (flag == "me") AppIcon.peach else AppIcon.daisy
)
