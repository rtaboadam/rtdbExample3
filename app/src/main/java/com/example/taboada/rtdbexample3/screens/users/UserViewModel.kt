package com.example.taboada.rtdbexample3.screens.users

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.viewModelScope
import com.example.taboada.rtdbexample3.model.ChatUser
import com.example.taboada.rtdbexample3.model.service.AccountService
import com.example.taboada.rtdbexample3.model.service.LogService
import com.example.taboada.rtdbexample3.model.service.StorageService
import com.example.taboada.rtdbexample3.screens.chats.TimeToTipTheScalesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    private val storageService: StorageService
): TimeToTipTheScalesViewModel(logService) {
    var users = mutableStateMapOf<String, ChatUser>()
        private set

    fun getUsers() {
        viewModelScope.launch {
            storageService.getUsers(onUsersChanged = ::onUserChanged)
        }
    }

    private fun onUserChanged(user: ChatUser) {
        users[user.userID] = user
    }

    fun removeListener() {
        viewModelScope.launch {
            storageService.removeUserListener()
        }
    }
}