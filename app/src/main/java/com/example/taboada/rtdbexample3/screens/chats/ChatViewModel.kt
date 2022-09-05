package com.example.taboada.rtdbexample3.screens.chats

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.viewModelScope
import com.example.taboada.rtdbexample3.CHAT_SCREEN
import com.example.taboada.rtdbexample3.SETTINGS_SCREEN
import com.example.taboada.rtdbexample3.model.Message
import com.example.taboada.rtdbexample3.model.service.LogService
import com.example.taboada.rtdbexample3.model.service.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val logService: LogService,
    private val storageService: StorageService
): TimeToTipTheScalesViewModel(logService) {
    var messages = mutableStateMapOf<String, Message>()
    private set

    fun addListener() {
        viewModelScope.launch(showErrorExceptionHandler) {
            storageService.addListener("chats", ::onNewMessage, ::onDeletedMessage)
        }
    }

    fun removeListener() {
        viewModelScope.launch(showErrorExceptionHandler) {
            storageService.removeListener()
        }
    }

    private fun onNewMessage(message: Message) {
        messages[message.id] = message
    }

    private fun onDeletedMessage(id: String) {
        messages.remove(id)
    }

    fun deleteMessage(id: String): () -> Unit {
        return {
            Log.w("Removing Chat with ID", id)
            storageService.deleteMessage(id)
        }
    }

    fun onAddClick(openScreen: (String) -> Unit) = openScreen(CHAT_SCREEN)
    fun onSettingsClick(openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)
}