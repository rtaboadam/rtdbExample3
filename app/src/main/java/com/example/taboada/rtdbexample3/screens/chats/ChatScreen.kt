package com.example.taboada.rtdbexample3.screens.chats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taboada.rtdbexample3.common.composable.ActionToolbar
import com.example.taboada.rtdbexample3.common.ext.smallSpacer
import com.example.taboada.rtdbexample3.common.ext.toolbarActions
import com.example.taboada.rtdbexample3.R.drawable as AppIcon
import com.example.taboada.rtdbexample3.R.string as AppText

@Composable
@ExperimentalMaterialApi
fun ChatScreen(
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = hiltViewModel(),
) {

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = { viewModel.onAddClick(openScreen) },
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary,
            modifier = modifier.padding(16.dp)
        ) { Icon(Icons.Filled.Add, "Add") }
    }) {
        val chats = viewModel.chats

        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            ActionToolbar(
                title = AppText.chats,
                modifier = Modifier.toolbarActions(),
                endActionIcon = AppIcon.ic_settings,
                endAction = { viewModel.onSettingsClick(openScreen) }
            )

            Spacer(modifier = Modifier.smallSpacer())

            Card {
                Column {
                    if (chats.values.isNotEmpty()) {
                        Text(text = "Private Chats", style = MaterialTheme.typography.h3)
                        Spacer(modifier = Modifier.smallSpacer())
                    }
                    LazyColumn {
                        items(chats.values.toList(), key = { it.chatID }) { chatItem ->
                            ChatDetailsItem(
                                chatDetails = chatItem,
                                onClickAction = { viewModel.onChatClick(openScreen, chatItem) })
                        }
                    }
                }
            }
        }
    }

    DisposableEffect(viewModel) {
        viewModel.initialize()
        onDispose {
            viewModel.removeListener()
        }
    }
}