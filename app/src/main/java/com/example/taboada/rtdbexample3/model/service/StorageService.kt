package com.example.taboada.rtdbexample3.model.service

import androidx.annotation.StringRes
import com.example.taboada.rtdbexample3.model.Message
import com.example.taboada.rtdbexample3.model.ChatDetails
import com.example.taboada.rtdbexample3.model.ChatUser
import com.example.taboada.rtdbexample3.screens.sign_up.SignUpUiState

interface StorageService {
    fun useEmulator(host: String, port: Int)
    fun getAllMyChats(userID: String, onChatChanged: (ChatDetails) -> Unit)
    fun removeListener()
    fun saveMessage(message: Message, onResult: (Throwable?) -> Unit)
    fun deleteMessage(id: String)
    fun updateUserId(oldUserId: String, newUserId: String, onResult: (Throwable?) -> Unit)
    fun getUsers(onUsersChanged: (ChatUser) -> Unit)
    fun removeUserListener()
    fun addNewChat(value: ChatDetails, onSuccess: () -> Unit)
    fun getConversation(
        chat: String,
        currentUser: String,
        onMessageAdded: (Message) -> Unit,
        onMessageUpdated: (Message) -> Unit,
        onMessageDeleted: (Message) -> Unit
    )
    fun sendMessage(chat:String, userId: String, msgContent: String)
    fun removeChatListener(chat: String)
    fun updateUserProfile(userId:String, user: SignUpUiState)
}