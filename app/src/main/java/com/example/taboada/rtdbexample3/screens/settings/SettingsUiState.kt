package com.example.taboada.rtdbexample3.screens.settings

data class SettingsUiState(
    val isAnonymousAccount: Boolean = true,
    val userID: String = "",
    val displayName: String = "",
)
