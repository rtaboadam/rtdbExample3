package com.example.taboada.rtdbexample3.screens.chats

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.viewModelScope
import com.example.taboada.rtdbexample3.CONVERSATION_SCREEN
import com.example.taboada.rtdbexample3.CREATE_CHAT_SCREEN
import com.example.taboada.rtdbexample3.SETTINGS_SCREEN
import com.example.taboada.rtdbexample3.model.ChatDetails
import com.example.taboada.rtdbexample3.model.service.AccountService
import com.example.taboada.rtdbexample3.model.service.LogService
import com.example.taboada.rtdbexample3.model.service.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val logService: LogService,
    private val storageService: StorageService,
    private val accountService: AccountService,
): TimeToTipTheScalesViewModel(logService) {
    var chats = mutableStateMapOf<String, ChatDetails>()
        private set

    fun initialize() {
        viewModelScope.launch {
            val userID = accountService.getUserId()
            storageService.getAllMyChats(userID, ::onChatChanged)
        }
    }

    fun removeListener() {
        viewModelScope.launch {
            storageService.removeListener()
        }
    }

    private fun onChatChanged(chatDetails: ChatDetails) {
        chats[chatDetails.chatID] = chatDetails
    }

    /**
     * Navigation handler functions
     */
    fun onAddClick(openScreen: (String) -> Unit) = openScreen(CREATE_CHAT_SCREEN)
    fun onSettingsClick(openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)
    fun onChatClick(openScreen: (String) -> Unit, chatDetails: ChatDetails) = openScreen("$CONVERSATION_SCREEN/{${chatDetails.chatID}}")
}