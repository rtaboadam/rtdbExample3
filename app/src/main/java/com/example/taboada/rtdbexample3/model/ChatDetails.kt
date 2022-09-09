package com.example.taboada.rtdbexample3.model

import androidx.annotation.DrawableRes

data class ChatDetails(
    val chatID: String = "",
    val members: Map<String, String> = mapOf(),
    val isPublic: Boolean = false
) {
    override fun toString(): String {
        return "ChatDetails:\n-chatID:${this.chatID}\n-members:${this.members.toString()}\n-isPublic:${isPublic.toString()}"
    }
}
