package com.example.taboada.rtdbexample3.screens.conversation

import com.example.taboada.rtdbexample3.model.Message

data class ConversationUiState(
    val chatID: String = "",
    val messages: List<Message> = listOf()
)
