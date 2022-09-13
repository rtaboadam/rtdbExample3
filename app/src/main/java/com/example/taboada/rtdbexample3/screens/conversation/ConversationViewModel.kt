package com.example.taboada.rtdbexample3.screens.conversation

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taboada.rtdbexample3.CHAT_SCREEN
import com.example.taboada.rtdbexample3.model.ChatUser
import com.example.taboada.rtdbexample3.model.Message
import com.example.taboada.rtdbexample3.model.service.AccountService
import com.example.taboada.rtdbexample3.model.service.LogService
import com.example.taboada.rtdbexample3.model.service.StorageService
import com.example.taboada.rtdbexample3.screens.chats.TimeToTipTheScalesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationViewModel @Inject constructor(
    private val logService: LogService,
    private val storageService: StorageService,
    private val accountService: AccountService,
): TimeToTipTheScalesViewModel(logService) {

    var currentUser = mutableStateOf(ChatUser())
        private set

    var conversationMessages = mutableStateMapOf<String, Message>()
        private set

    fun initialize(chatID: String) {
        viewModelScope.launch {
            currentUser.value = currentUser.value.copy(userID = accountService.getUserId())
            storageService.getConversation(
                currentUser = accountService.getUserId(),
                chat = chatID,
                onMessageAdded = ::onConversationAdded,
                onMessageUpdated = ::onMessageUpdated,
                onMessageDeleted = ::onMessageDeleted
            )
        }
    }
    fun removeListener(chat: String) {
        viewModelScope.launch {
            storageService.removeChatListener(chat)
        }
    }

    fun sendMessage(chat:String, msgContent: String) {
        viewModelScope.launch {
            storageService.sendMessage(chat, accountService.getUserId(), msgContent)
        }
    }

    private fun onConversationAdded(message: Message) {
        conversationMessages[message.id] = message
    }

    private fun onMessageUpdated(message: Message) {
        conversationMessages[message.id] = message
    }

    private fun onMessageDeleted(message: Message) {
        conversationMessages.remove(message.id)
    }

}