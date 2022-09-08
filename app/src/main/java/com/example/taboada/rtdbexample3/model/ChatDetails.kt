package com.example.taboada.rtdbexample3.model

import androidx.annotation.DrawableRes

data class ChatDetails(
    val chatID: String = "",
    val members: MutableMap<String, String> = mutableMapOf(),
    val isPublic: Boolean = false
)
