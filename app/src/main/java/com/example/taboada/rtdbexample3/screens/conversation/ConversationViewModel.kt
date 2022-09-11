package com.example.taboada.rtdbexample3.screens.conversation

import androidx.compose.runtime.mutableStateOf
import com.example.taboada.rtdbexample3.CHAT_SCREEN
import com.example.taboada.rtdbexample3.model.service.LogService
import com.example.taboada.rtdbexample3.model.service.StorageService
import com.example.taboada.rtdbexample3.screens.chats.TimeToTipTheScalesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConversationViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
): TimeToTipTheScalesViewModel(logService) {

    var uiState = mutableStateOf(ConversationUiState())

    fun initialize() {}

    fun onGoToChatScreen(openScreen: (String) -> Unit) = openScreen(CHAT_SCREEN)
}