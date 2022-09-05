package com.example.taboada.rtdbexample3.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taboada.rtdbexample3.common.composable.*
import com.example.taboada.rtdbexample3.common.ext.basicButton
import com.example.taboada.rtdbexample3.common.ext.fieldModifier
import com.example.taboada.rtdbexample3.common.ext.textButton
import com.example.taboada.rtdbexample3.R.string as AppText

@Composable
fun LoginScreen(
    openPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    BasicToolbar(title = AppText.login_details)

    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(uiState.email, onNewValue = viewModel::onEmailChange, Modifier.fieldModifier())
        PasswordField(value = uiState.password, onNewValue = viewModel::onEmailChange, Modifier.fieldModifier())
        BasicButton(text = AppText.sign_in, modifier = Modifier.basicButton()) {
            viewModel.onSignInClick(openPopUp)
        }
        BasicTextButton(text = AppText.forgot_password, modifier = Modifier.textButton()) {
            viewModel.onForgotPasswordClick()
        }
    }
}