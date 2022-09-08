package com.example.taboada.rtdbexample3.screens.createChat


data class CreateChatUiState(
    val members: List<String> = listOf(),
    val isPublic: Boolean = false
)