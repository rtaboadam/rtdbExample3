package com.example.taboada.rtdbexample3.screens.conversation

import android.view.WindowInsets
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.makeitso.theme.TimeToTipTheScalesTheme
import com.example.taboada.rtdbexample3.model.Message

@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewConversationScreen() {
    TimeToTipTheScalesTheme {
        ConversationScreen(
            conversationState = ConversationDetailState(chatID = "UUID_CHAT")
        )
    }
}


@ExperimentalMaterialApi
@Composable
fun ConversationScreen(
    conversationState: ConversationDetailState,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberLazyListState()
    val message = listOf<Message>(
        Message(
            id = "UUID_CHAT_CONVERSATION_1",
            content = "Hello World!",
            priority = "A",
            timestamp = "September, 9th",
            flag = "me",
            userId = "FIREBASE_UID_USER_X"
        ),
        Message(
            id = "UUID_CHAT_CONVERSATION_1",
            content = "Rytsas!",
            priority = "A",
            timestamp = "September, 9th",
            flag = "meaa",
            userId = "FIREBASE_UID_USER_X"
        )
    )

    Surface(modifier = Modifier) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                Messages(
                    messages = message,
                    navigateToProfile = {},
                    scrollState = scrollState
                )
                UserInput(
                    chatReceiver = "FIREBASE",
                    onMessageSent = {}
                )

            }
        }
    }


//    Surface(modifier = Modifier) {
//        Box(modifier = Modifier.fillMaxSize()) {
//            Column(
//                Modifier
//                    .fillMaxSize()
//            ) {
//                TopAppBar (
//                    title = { Text(conversationState.chatID) },
//                )
//                Messages(
//                    messages = message,
//                    navigateToProfile = {},
//                    scrollState = scrollState
//                )
//                UserInput(
//                    chatReceiver = "FIREBASE",
//                    onMessageSent = {}
//                )
//            }
//        }
//
//    }
//    Column(
//        modifier = modifier
//            .fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,
//    ) {
//        UserInput(
//            chatReceiver = "FIREBASE",
//            onMessageSent = {})
//    }
}

@ExperimentalMaterialApi
@Composable
fun Messages(
    messages: List<Message>,
    navigateToProfile: (String) -> Unit,
    scrollState: LazyListState,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    Column(modifier = modifier) {
        LazyColumn(
            reverseLayout = true,
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
        ) {
            for (index in messages.indices) {
                val prevAuthor = messages.getOrNull(index - 1)?.userId
                val nextAuthor = messages.getOrNull(index + 1)?.userId
                val content = messages[index]
                val isFirstMessageByAuthor = prevAuthor != content.userId
                val isLastMessageByAuthor = nextAuthor != content.userId

                if (index == messages.size - 1) {
                    item {
                        DayHeader(dayString = "30 September")
                    }
                }

                item {
                    Message(
                        onAuthorClick = {},
                        msg = content,
                        isUserMe = content.userId == "me",
                        isFirstMessageByAuthor = isFirstMessageByAuthor,
                        isLastMessageByAuthor = isLastMessageByAuthor
                    )
                }
            }
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun Message(
    onAuthorClick: (String) -> Unit,
    msg: Message,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean
) {
    Text(text = msg.content)
    /*val borderColor = if (isUserMe) {
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.secondary
    }
    
    val spaceBetweenAuthors = if (isLastMessageByAuthor) Modifier.padding(top = 8.dp) else Modifier
    
    Row( modifier = spaceBetweenAuthors ) {
        if (isLastMessageByAuthor) {
            Image(
                modifier = Modifier
                    .clickable(onClick = { onAuthorClick(msg.userId) })
                    .padding(horizontal = 16.dp)
                    .size(42.dp)
                    .border(1.5.dp, borderColor, CircleShape)
                    .border(3.dp, MaterialTheme.colors.surface, CircleShape)
                    .clip(CircleShape)
                    .align(Alignment.Top),
                painter = painterResource(msg.authorImage),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        } else {
            Spacer(modifier = Modifier.width(74.dp))
        }
        AuthorAndTextMessage(
            msg = msg,
            isUserMe = isUserMe,
            isFirstMessageByAuthor = isFirstMessageByAuthor,
            isLastMessageByAuthor = isLastMessageByAuthor,
            authorClicked = onAuthorClick,
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f)
        )
    }*/
}

@ExperimentalMaterialApi
@Composable
fun AuthorAndTextMessage(
    msg: Message,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean,
    authorClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier) {
        if (isLastMessageByAuthor) {
            AuthorNameTimestamp(msg)
        }
    }
}
@ExperimentalMaterialApi
@Composable
private fun AuthorNameTimestamp(msg: Message) {
    // Combine author and timestamp for a11y.
    Row(modifier = Modifier.semantics(mergeDescendants = true) {}) {
        Text(
            text = msg.userId,
            style = MaterialTheme.typography.h3,
            modifier = Modifier
                .alignBy(LastBaseline)
                .paddingFrom(LastBaseline, after = 8.dp) // Space to 1st bubble
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = msg.timestamp,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.alignBy(LastBaseline),
            color = MaterialTheme.colors.onSurface
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun ChatItemBubble(
    message: Message,
    isUserMe: Boolean,
    authorClicked: (String) -> Unit
) {

    val backgroundBubbleColor = if (isUserMe) {
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.surface
    }

    Column {
        Surface(
            color = backgroundBubbleColor,
            shape = ChatBubbleShape
        ) {
            Text(text = message.content)
//            ClickableMessage(
//                message = message,
//                isUserMe = isUserMe,
//                authorClicked = authorClicked
//            )
        }
    }
}

private val ChatBubbleShape = RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)

@Composable
fun DayHeader(dayString: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .height(16.dp)
    ) {
        DayHeaderLine()
        Text(
            text = dayString,
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onSurface
        )
        DayHeaderLine()
    }
}

@Composable
private fun RowScope.DayHeaderLine() {
    Divider(
        modifier = Modifier
            .weight(1f)
            .align(Alignment.CenterVertically),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
    )
}

//@Composable
//fun ClickableMessage(
//    message: Message,
//    isUserMe: Boolean,
//    authorClicked: (String) -> Unit
//) {
//    val uriHandler = LocalUriHandler.current
//    
//    val styledMessage = messageFormatter(
//        text = message.content,
//        primary = isUserMe
//    )
//
//    ClickableText(
//        text = styledMessage,
//        style = MaterialTheme.typography.h3.copy(color = LocalContentColor.current),
//        modifier = Modifier.padding(16.dp),
//        onClick = {
//            styledMessage
//                .getStringAnnotations(start = it, end = it)
//                .firstOrNull()
//                ?.let { annotation ->
//                    when (annotation.tag) {
//                        SymbolAnnotationType.LINK.name -> uriHandler.openUri(annotation.item)
//                        SymbolAnnotationType.PERSON.name -> authorClicked(annotation.item)
//                        else -> Unit
//                    }
//                }
//        }
//    )
//}