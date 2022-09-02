package com.example.taboada.rtdbexample3.screens.chats

import androidx.lifecycle.ViewModel
import com.example.makeitso.common.snackbar.SnackbarManager
import com.example.taboada.rtdbexample3.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.taboada.rtdbexample3.model.service.LogService
import kotlinx.coroutines.CoroutineExceptionHandler

open class TimeToTipTheScalesViewModel(private val logService: LogService): ViewModel() {
    open val showErrorExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable)
    }

    open val logErrorExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        logService.logNonFatalCrash(throwable)
    }

    private fun onError(error: Throwable) {
        SnackbarManager.showMessage(error.toSnackbarMessage())
        logService.logNonFatalCrash(error)
    }



}