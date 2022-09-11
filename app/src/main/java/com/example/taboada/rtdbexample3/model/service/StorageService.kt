package com.example.taboada.rtdbexample3.model.service

import com.example.taboada.rtdbexample3.model.Message
import com.example.taboada.rtdbexample3.model.ChatDetails
import com.example.taboada.rtdbexample3.model.ChatUser

interface StorageService {
//    fun addListener(
//        userId: String,
//        onNewMessage: (Message) -> Unit,
//        onDeletedMessage: (String) -> Unit
//    )
    fun useEmulator(host:String, port:Int)
    fun getAllMyChats(userID: String, onChatChanged: (ChatDetails) -> Unit)
    fun removeListener()
    fun saveMessage(message: Message, onResult: (Throwable?) -> Unit)
    fun deleteMessage(id: String)
    fun updateUserId(oldUserId: String, newUserId: String, onResult: (Throwable?) -> Unit)
    fun getUsers(onUsersChanged: (ChatUser) -> Unit)
    fun removeUserListener()
    fun addNewChat(value: ChatDetails, onSuccess: () -> Unit)
}