package com.example.taboada.rtdbexample3.screens.users//package com.example.taboada.rtdbexample3.screens.users

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel


//@ExperimentalMaterialApi
//@Composable
//fun UserScreen(
//    modifier: Modifier = Modifier,
//    onClickAction: () -> Unit,
//    viewModel: UserViewModel = hiltViewModel()
//) {
//    val users =  viewModel.users
//    LazyColumn(modifier = modifier) {
//        items(users.values.toList(), key = {it.userID}) { userItem ->
//            UserCard(
//                user = userItem,
//                onClickAction = onClickAction
//            )
//        }
//    }
//
//    DisposableEffect(viewModel) {
//        viewModel.getUsers()
//        onDispose {
//            viewModel.removeListener()
//        }
//    }
//}