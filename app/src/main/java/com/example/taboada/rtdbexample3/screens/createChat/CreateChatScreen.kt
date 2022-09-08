package com.example.taboada.rtdbexample3.screens.createChat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taboada.rtdbexample3.common.composable.ActionToolbar
import com.example.taboada.rtdbexample3.common.ext.smallSpacer
import com.example.taboada.rtdbexample3.common.ext.toolbarActions
import com.example.taboada.rtdbexample3.screens.users.UserViewModel
import com.example.taboada.rtdbexample3.R.drawable as AppIcon
import com.example.taboada.rtdbexample3.R.string as AppText


@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewCreateChatScreen() {
    CreateChatScreen(openScreen = {})
}

@ExperimentalMaterialApi
@Composable
fun CreateChatScreen(
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = hiltViewModel(),
    createScreenViewModel: CreateChatViewModel = hiltViewModel()
) {
    var users = userViewModel.users
    var members = createScreenViewModel.members

    Scaffold (
        floatingActionButton = {
            FloatingActionButton(
                onClick = { createScreenViewModel.onChatAdded(openScreen) },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                modifier = modifier.padding(16.dp)
            ) { Icon(Icons.Filled.Done, "Added Create Chat") }
        }
            ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            ActionToolbar(
                title = AppText.create_chat,
                modifier = Modifier.toolbarActions(),
                endAction = { createScreenViewModel.onChatAdded(openScreen) },
                endActionIcon = AppIcon.ic_check
            )
            Card(
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Column( modifier = Modifier.padding(15.dp)) {
                    Text(text = "New Chat", style = MaterialTheme.typography.h3)
                    Spacer(modifier = Modifier.smallSpacer())
                    Text(text = "Type", style = MaterialTheme.typography.h4)
                    RadioButtonSample(onTypeChanged = createScreenViewModel::onTypeChanged)
                    Text(text = "Chatter Members", style = MaterialTheme.typography.h4)
                    Spacer(modifier = Modifier.smallSpacer())
                    LazyColumn {
                        items(members.keys.toList(), key = {it}) { userID ->
                            Text("Chatter: $userID")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.smallSpacer())
            Card() {
                Column(modifier = Modifier.padding(15.dp)) {
                    Text(text = "Current Verified Users", style = MaterialTheme.typography.h5)

                    LazyColumn {
                        items(users.values.toList(), key = { it.userID}) { userItem ->
                            UserCardCheckbox(user = userItem, onClickAction = createScreenViewModel::onMemberUpdate )
                        }
                    }
                }

            }
        }
    }
    

    DisposableEffect(userViewModel) {
        userViewModel.getUsers()
        onDispose {
            userViewModel.removeListener()
        }
    }
}
 

@Composable
fun RadioButtonSample(onTypeChanged: (Boolean) -> Unit) {
    val radioOptions = listOf("public", "private")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[1] ) }
    Column {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            onTypeChanged(text == "public")
                        }
                    )
//                    .padding(horizontal = 4.dp)
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = { onOptionSelected(text) }
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.body1.merge(),
//                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    text: String,
    placeholder: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    onChange: (String) -> Unit = {},
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyBoardActions: KeyboardActions = KeyboardActions(),
    isEnabled: Boolean = true
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        onValueChange = onChange,
        leadingIcon = leadingIcon,
        textStyle = TextStyle(fontSize = 18.sp),
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
        keyboardActions = keyBoardActions,
        enabled = isEnabled,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Gray,
            disabledBorderColor = Color.Gray,
            disabledTextColor = Color.Black
        ),
        placeholder = {
            Text(text = placeholder, style = TextStyle(fontSize = 18.sp, color = Color.LightGray))
        }
    )
}

