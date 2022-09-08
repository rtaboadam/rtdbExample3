package com.example.taboada.rtdbexample3.screens.createChat

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateMap
import androidx.lifecycle.viewModelScope
import com.example.taboada.rtdbexample3.CHAT_SCREEN
import com.example.taboada.rtdbexample3.model.ChatDetails
import com.example.taboada.rtdbexample3.model.ChatUser
import com.example.taboada.rtdbexample3.model.service.AccountService
import com.example.taboada.rtdbexample3.model.service.LogService
import com.example.taboada.rtdbexample3.model.service.StorageService
import com.example.taboada.rtdbexample3.screens.chats.TimeToTipTheScalesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateChatViewModel @Inject constructor(
    private val logService: LogService,
    private val accountService: AccountService,
    private val storageService: StorageService
): TimeToTipTheScalesViewModel(logService) {
//    var uiState = mutableStateOf(CreateChatUiState())
    var uiState = mutableStateOf(ChatDetails())

    val members get() = uiState.value.members
    val isPublic get()= uiState.value.isPublic

    fun onMemberUpdate(checked:Boolean, user: ChatUser) {
        if (checked) {
            uiState.value.members[user.userID] = "chatter"
        } else {
            uiState.value.members.remove(user.userID)
        }
//        uiState.value = if(checked) {
////            uiState.value.copy(members = members + (user.userID to "chatter"))
//        } else {
////            uiState.value.copy(members = members.filterKeys { x -> x != user.userID })
//        }
//        Log.w("DEBUG", members.toString())
    }

    fun onTypeChanged(isPublic: Boolean) {
        uiState.value = uiState.value.copy(isPublic = isPublic)
    }

    fun onChatAdded(openScreen: (String) -> Unit)  {

        viewModelScope.launch {
//            uiState.value.copy(members = members + (accountService.getUserId() to "owner") )
            storageService.addNewChat(uiState.value) {
                uiState = mutableStateOf(ChatDetails())
                openScreen(CHAT_SCREEN)
            }
        }

//        uiState = mutableStateOf(CreateChatUiState())
//        openScreen(CHAT_SCREEN)
    }
}