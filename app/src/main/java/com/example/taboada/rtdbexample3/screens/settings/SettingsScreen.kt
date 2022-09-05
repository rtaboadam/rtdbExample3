package com.example.taboada.rtdbexample3.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taboada.rtdbexample3.common.composable.*
import com.example.taboada.rtdbexample3.common.ext.card
import com.example.taboada.rtdbexample3.common.ext.spacer
import com.example.taboada.rtdbexample3.R.string as AppText
import com.example.taboada.rtdbexample3.R.drawable as AppIcon

@ExperimentalMaterialApi
@Composable
fun SettingsScreen(
    restartApp: (String) -> Unit,
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    LaunchedEffect(Unit) { viewModel.initialize() }

    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicToolbar(title = AppText.settings)
        Spacer(modifier = Modifier.spacer())

        if (uiState.isAnonymousAccount) {
            RegularCardEditor(AppText.sign_in, AppIcon.ic_sign_in, content = "", modifier = Modifier.card()) {
                viewModel.onLoginClick(openScreen)
            }

            RegularCardEditor(title = AppText.create_account, icon = AppIcon.ic_create_account, content = "" , modifier = Modifier.card()) {
                viewModel.onSignUpClick(openScreen)
            }
        } else {
        }
        
    }
}

@ExperimentalMaterialApi
@Composable
private fun DeleteMyAccountCard(deleteMyAccount: () -> Unit) {
    var showWarningDialog by remember {
        mutableStateOf(false)
    }
    
    DangerousCardEditor(
        title = AppText.delete_my_account,
        icon = AppIcon.ic_delete_my_account, 
        content = "", 
        modifier = Modifier.card() ) {
        showWarningDialog = true
    }
    
    if(showWarningDialog) {
        AlertDialog(
            title = { Text(text = "Delete Account?")},
            text = { Text(text =  stringResource(id = AppText.delete_account_description))},
            dismissButton = { DialogCancelButton(text = AppText.cancel) {showWarningDialog = false} },
            confirmButton = {
                DialogConfirmButton(text = AppText.delete_my_account) {
                    deleteMyAccount()
                    showWarningDialog = false
                }
            },
            onDismissRequest = {showWarningDialog = false}
        )
    }
}
