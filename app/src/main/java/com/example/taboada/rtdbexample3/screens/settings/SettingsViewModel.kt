package com.example.taboada.rtdbexample3.screens.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.taboada.rtdbexample3.LOGIN_SCREEN
import com.example.taboada.rtdbexample3.SIGN_UP_SCREEN
import com.example.taboada.rtdbexample3.SPLASH_SCREEN
import com.example.taboada.rtdbexample3.model.service.AccountService
import com.example.taboada.rtdbexample3.model.service.LogService
import com.example.taboada.rtdbexample3.model.service.StorageService
import com.example.taboada.rtdbexample3.screens.chats.TimeToTipTheScalesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    private val storageService: StorageService
) : TimeToTipTheScalesViewModel(logService) {
    var uiState = mutableStateOf(SettingsUiState())
        private set

    fun initialize() {
        uiState.value = SettingsUiState(accountService.isAnonymousUser())
    }

    fun onLoginClick(openScreen: (String) -> Unit) = openScreen(LOGIN_SCREEN)

    fun onSignUpClick(openScreen: (String) -> Unit) = openScreen(SIGN_UP_SCREEN)

    fun onSignOutClick(restartApp: (String) -> Unit) {
        viewModelScope.launch(showErrorExceptionHandler) {
            accountService.signOut()
            restartApp(SPLASH_SCREEN)
        }
    }

    fun onDeleteMyAccountClick(restartApp: (String) -> Unit) {
        viewModelScope.launch(showErrorExceptionHandler) {
/*
            storageService.deleteAllForUser(accountService.getUserId()) { error ->
                if (error == null) deleteAccount(restartApp) else onError(error)
            }
*/
        }
    }

    private fun deleteAccount(restartApp: (String) -> Unit) {
        viewModelScope.launch(showErrorExceptionHandler) {
            accountService.deleteAccount { error ->
                if (error == null) restartApp(SPLASH_SCREEN) else onError(error)
            }
        }
    }
}