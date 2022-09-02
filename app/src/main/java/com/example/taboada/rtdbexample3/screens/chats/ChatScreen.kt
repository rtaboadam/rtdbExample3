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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
@ExperimentalMaterialApi
fun ChatScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = hiltViewModel()
) {

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = { /*TODO*/ },
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.primary,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(Icons.Filled.Add, "Add")
        }
    }) {
        val messages = viewModel.messages
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {

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
        }
    }

    DisposableEffect(viewModel) {
        viewModel.addListener()
        onDispose {  viewModel.removeListener() }
    }
}