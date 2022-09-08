package com.example.taboada.rtdbexample3.screens.sign_up

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val displayName: String = ""
)