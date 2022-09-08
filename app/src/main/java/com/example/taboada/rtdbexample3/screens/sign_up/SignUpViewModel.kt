package com.example.taboada.rtdbexample3.screens.sign_up

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.makeitso.common.snackbar.SnackbarManager
import com.example.taboada.rtdbexample3.SETTINGS_SCREEN
import com.example.taboada.rtdbexample3.SIGN_UP_SCREEN
import com.example.taboada.rtdbexample3.common.ext.isValidEmail
import com.example.taboada.rtdbexample3.common.ext.isValidPassword
import com.example.taboada.rtdbexample3.common.ext.passwordMatches
import com.example.taboada.rtdbexample3.model.service.AccountService
import com.example.taboada.rtdbexample3.model.service.LogService
import com.example.taboada.rtdbexample3.model.service.StorageService
import com.example.taboada.rtdbexample3.screens.chats.TimeToTipTheScalesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.taboada.rtdbexample3.R.string as AppText

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService,
    private val logService: LogService
): TimeToTipTheScalesViewModel(logService){
    var uiState = mutableStateOf(SignUpUiState())
        private set

    private val email get() = uiState.value.email
    private val password get() = uiState.value.password
    private val displayName get() = uiState.value.displayName

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onDisplayNameChange(newValue: String) {
        uiState.value = uiState.value.copy(displayName = newValue)
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (!password.isValidPassword()) {
            SnackbarManager.showMessage(AppText.password_error)
            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            SnackbarManager.showMessage(AppText.password_match_error)
            return
        }

        viewModelScope.launch(showErrorExceptionHandler) {
            val oldUserId = accountService.getUserId()
            accountService.createAccount(email, password) { error ->
                if (error == null) {
                    linkWithEmail()
                    updateUserId(oldUserId, openAndPopUp)
                } else onError(error)
            }
        }
    }

    private fun linkWithEmail() {
        viewModelScope.launch(showErrorExceptionHandler) {
            accountService.linkAccount(email, password) { error ->
                if (error != null) logService.logNonFatalCrash(error)
            }
        }
    }

    private fun updateUserId(oldUserId: String, openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch(showErrorExceptionHandler) {
            val newUserId = accountService.getUserId()
            viewModelScope.launch(showErrorExceptionHandler){
                accountService.updateProfile(displayName)
            }
            openAndPopUp(SETTINGS_SCREEN, SIGN_UP_SCREEN)
        }
    }
}