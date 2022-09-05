package com.example.taboada.rtdbexample3.screens.chats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.makeitso.theme.TimeToTipTheScalesTheme
import com.example.taboada.rtdbexample3.common.composable.ActionToolbar
import com.example.taboada.rtdbexample3.common.composable.BasicField
import com.example.taboada.rtdbexample3.common.composable.BasicTextButton
import com.example.taboada.rtdbexample3.common.ext.smallSpacer
import com.example.taboada.rtdbexample3.common.ext.toolbarActions
import com.example.taboada.rtdbexample3.R.string as AppText
import com.example.taboada.rtdbexample3.R.drawable as AppIcon

@Composable
@ExperimentalMaterialApi
fun ChatScreen(
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = hiltViewModel()
) {

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = { viewModel.onAddClick(openScreen) },
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = Color.White,
            modifier = modifier.padding(16.dp)
        ) {
            Icon(Icons.Filled.Add, "Add")
        }
    }) {
        val messages = viewModel.messages

        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {

            ActionToolbar(
                title = AppText.chats,
                modifier = Modifier.toolbarActions(),
                endActionIcon = AppIcon.ic_settings,
                endAction = { viewModel.onSettingsClick(openScreen) }
            ) 

            Spacer(modifier = Modifier.smallSpacer())

            LazyColumn {
                items(messages.values.toList()) { messageItem ->
                    Row {
                        Text(text = messageItem.id)
                        Text(text = messageItem.content)
                        IconButton(onClick =  viewModel.deleteMessage(messageItem.id) ) {
                            Icon(Icons.Filled.Delete, "Delete Message")
                        }
                    }
                }
            }

            BasicField(text = AppText.chats, value = "asjdajksdas" , onNewValue = {})
        }
    }

    DisposableEffect(viewModel) {
        viewModel.addListener()
        onDispose {  viewModel.removeListener() }
    }
}


@ExperimentalMaterialApi
@Composable
fun ChatCardDetail() {
    Card() {
        
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun ChatScreenPreview() {
    ChatCardDetail()
}