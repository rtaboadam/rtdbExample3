package com.example.taboada.rtdbexample3.model.service

import com.example.taboada.rtdbexample3.model.Message

interface StorageService {
    fun addListener(
        userId: String,
        onNewMessage: (Message) -> Unit,
        onDeletedMessage: (String) -> Unit
    )
    fun removeListener()
    fun saveMessage(message: Message, onResult: (Throwable?) -> Unit)
    fun deleteMessage(id: String)
    fun updateUserId(oldUserId: String, newUserId: String, onResult: (Throwable?) -> Unit)
}