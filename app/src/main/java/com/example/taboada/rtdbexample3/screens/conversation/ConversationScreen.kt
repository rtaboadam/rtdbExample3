package com.example.taboada.rtdbexample3.screens.conversation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel


@ExperimentalMaterialApi
@Composable
fun ConversationScreen(
    chatID: String,
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ConversationViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Chat de $chatID")
        Button(onClick = { viewModel.onGoToChatScreen(openScreen) }) {
            Text("Go back to chat room")
        }
    }
}
