package com.example.taboada.rtdbexample3.screens.conversation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AlternateEmail
import androidx.compose.material.icons.outlined.Mood
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taboada.rtdbexample3.R

enum class InputSelector {
    NONE,
    MAP,
    DM,
    EMOJI,
    PHONE,
    PICTURE
}

enum class EmojiStickerSelector {
    EMOJI,
    STICKER
}

@ExperimentalMaterialApi
@Preview
@Composable
fun UserInputPreview() {
    UserInput(
        chatReceiver = "#EPAM",
        onMessageSent = {})
}

@ExperimentalMaterialApi
@Composable
fun UserInput(
    chatReceiver: String,
    onMessageSent: (String) -> Unit,
    modifier: Modifier = Modifier,
    resetScroll: () -> Unit = {}
) {

    var currentInputSelector by rememberSaveable { mutableStateOf(InputSelector.NONE) }
    val dismissKeyboard = { currentInputSelector = InputSelector.NONE }
    var textState by remember { mutableStateOf(TextFieldValue()) }
    var textFieldFocusState by remember { mutableStateOf(false) }

    androidx.compose.material.Surface() {
        Column(modifier = modifier) {
            UserInputText(
                chatTextTitle = chatReceiver,
                onTextChanged = { textState = it },
                textFieldValue = textState,
                keyboardShown = currentInputSelector == InputSelector.NONE && textFieldFocusState,
                onTextFieldFocused = { focused ->
                    if (focused) {
                        currentInputSelector = InputSelector.NONE
                        resetScroll()
                    }
                },
                focusState = textFieldFocusState
            )

            UserInputSelector(
                onSelectorChange = { currentInputSelector = it },
                sendMessageEnabled = textState.text.isNotBlank(),
                onMessageSent = {
                    onMessageSent(textState.text)
                    // Reset text field and close keyboard
                    textState = TextFieldValue()
                    // Move scroll to botton
                    resetScroll()
                    dismissKeyboard()
                },
                currentInputSelector = currentInputSelector
            )
        }

    }
}

@ExperimentalMaterialApi
@Composable
private fun UserInputText(
    chatTextTitle: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onTextChanged: (TextFieldValue) -> Unit,
    textFieldValue: TextFieldValue,
    keyboardShown: Boolean,
    onTextFieldFocused: (Boolean) -> Unit,
    focusState: Boolean
) {
    val a11ylabel = stringResource(id = R.string.textfield_desc)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .semantics {
                contentDescription = a11ylabel
                keyboardShown
            },
        horizontalArrangement = Arrangement.End
    ) {
       Surface {
           Box(
               modifier = Modifier
                   .height(64.dp)
                   .weight(1f)
                   .align(Alignment.Bottom)
           ) {
               var lastFocusState by remember { mutableStateOf(false) }
               BasicTextField(
                   value = textFieldValue,
                   onValueChange = { onTextChanged(it) },
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(start = 32.dp)
                       .align(Alignment.CenterStart)
                       .onFocusChanged { state ->
                           if (lastFocusState != state.isFocused) {
                               onTextFieldFocused(state.isFocused)
                           }
                           lastFocusState = state.isFocused
                       },
                   keyboardOptions = KeyboardOptions(
                       keyboardType = keyboardType,
                       imeAction = ImeAction.Send
                   ),
                   maxLines = 1,
                   cursorBrush =  SolidColor(LocalContentColor.current),
                   textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current)
               )

               val disableContentColor =
                   MaterialTheme.colors.onSurface
               if (textFieldValue.text.isEmpty() && !focusState) {
                   Text(
                       modifier = Modifier
                           .align(Alignment.CenterStart)
                           .padding(start = 32.dp),
                       text = "Message to $chatTextTitle",
                       style = MaterialTheme.typography.body2.copy(color = disableContentColor)
                   )

               }
           }
       }
    }
}

@Composable
private fun UserInputSelector(
    onSelectorChange: (InputSelector) -> Unit,
    sendMessageEnabled: Boolean,
    onMessageSent: () -> Unit,
    currentInputSelector: InputSelector,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(72.dp)
            .wrapContentHeight()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        InputSelectorButton(
            onClick = { onSelectorChange(InputSelector.EMOJI) },
            icon = Icons.Outlined.Mood,
            selected = currentInputSelector == InputSelector.EMOJI,
            description = stringResource(id = R.string.emoji_selector_bt_desc)
        )
        InputSelectorButton(
            onClick = { onSelectorChange(InputSelector.DM) },
            icon = Icons.Outlined.AlternateEmail,
            selected = currentInputSelector == InputSelector.DM,
            description = stringResource(id = R.string.dm_desc)
        )

        val border = if (!sendMessageEnabled ) {
            BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
            )
        } else {
            null
        }
        Spacer(modifier = Modifier.weight(1f))

        val disableContentColor = MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
        val buttonColors = ButtonDefaults.buttonColors(
            disabledContentColor = disableContentColor,
            disabledBackgroundColor = Color.Transparent
        )

        Button (
            modifier = Modifier.height(36.dp),
            enabled = sendMessageEnabled,
            onClick = onMessageSent,
            colors = buttonColors,
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                stringResource(id = R.string.send),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

        }
    }
}

@Composable
private fun InputSelectorButton(
    onClick: () -> Unit,
    icon: ImageVector,
    description: String,
    selected: Boolean
) {
    val backgroundModifier = if (selected) {
        Modifier.background(
            color = MaterialTheme.colors.secondary,
            shape = RoundedCornerShape(14.dp)
        )
    } else {
        Modifier
    }
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(56.dp)
            .then(backgroundModifier)
    ) {
        val tint = if (selected) {
            MaterialTheme.colors.onSecondary
        } else {
            MaterialTheme.colors.secondary
        }
        Icon(
            icon,
            tint = tint,
            modifier = Modifier.padding(16.dp),
            contentDescription = description
        )
    }
}