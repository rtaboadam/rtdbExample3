@file:OptIn(ExperimentalMaterialApi::class)

package com.example.taboada.rtdbexample3.screens.conversation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taboada.rtdbexample3.model.ChatUser
import com.example.taboada.rtdbexample3.model.Message
import com.example.taboada.rtdbexample3.model.data.getConversationMessages


@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewConversationScreen() {
    ConversationScreen(
        chatID = "XXX-chat",
        openScreen = {})
}

@ExperimentalMaterialApi
@Composable
fun ConversationScreen(
    chatID: String,
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ConversationViewModel = hiltViewModel(),
) {

    var scrollBehavior = rememberScrollState()
    var scrollState = rememberLazyListState()

    val currentUser = viewModel.currentUser
    val messages = viewModel.conversationMessages

    Surface(
        modifier = modifier
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .verticalScroll(scrollBehavior)
            ) {
                TopAppBar(
                    title = { Text(chatID) },
                )
                Messages(
                    messages = messages.values.toList().sortedByDescending { it.timestamp },
                    modifier = Modifier.weight(1f),
                    scrollState = scrollState,
                    navigationToProfile = {},
                    userMe = currentUser.value
                )
                UserInput(
                    onMessageSent = { viewModel.sendMessage(chatID, it) },
                    resetScroll = {},
                )
            }
        }
    }

    DisposableEffect(viewModel) {
        viewModel.initialize(chatID)
        onDispose {
            viewModel.removeListener(chatID)
        }
    }

}


@Composable
fun Messages(
    messages: List<Message>,
    modifier: Modifier,
    scrollState: LazyListState,
    navigationToProfile: (String) -> Unit,
    userMe: ChatUser = ChatUser("me")
) {
    val userIdMe = userMe.userID
    Text(text = "Messages")
    Box(modifier = modifier) {
        LazyColumn(
            reverseLayout = true,
            state = scrollState,
            modifier = Modifier
                .testTag("CONVERSTATION_MESSAGE_TAG")
                .fillMaxSize()
        ) {
            for (index in messages.indices) {
                val prevAuthor = messages.getOrNull(index - 1)?.userId
                val nextAuthor = messages.getOrNull(index + 1)?.userId
                val content = messages[index]
                val isFirstMessageByAuthor = prevAuthor != content.userId
                val isLastMessageByAuthor = nextAuthor != content.userId

                item {
                    MessageComposable(
                        onAuthorClick = { name -> navigationToProfile(name) },
                        msg = content,
                        isUserMe = content.userId == userIdMe,
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
fun MessageComposable(
    onAuthorClick: (String) -> Unit,
    msg: Message,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean,
    modifier: Modifier = Modifier
) {
    val spaceBetweenAuthors = if (isLastMessageByAuthor) Modifier.padding(top = 8.dp) else Modifier
    Row(modifier = spaceBetweenAuthors) {
        if (isLastMessageByAuthor) {
            // Avatar
            Image(
                modifier = Modifier
                    .clickable(onClick = { onAuthorClick(msg.userId) })
                    .padding(horizontal = 16.dp)
                    .size(42.dp)
                    .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
                    .border(3.dp, MaterialTheme.colors.surface, CircleShape)
                    .clip(CircleShape)
                    .align(Alignment.Top),
                painter = painterResource(id = msg.authorImage),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
        } else {
            // Space under avatar
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
    }
}

@Composable
fun AuthorAndTextMessage(
    msg: Message,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean,
    authorClicked: (String) -> Unit,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        if (isLastMessageByAuthor) {
            AuthorNameTimestamp(msg)
        }
        ChatItemBubble(msg, isUserMe, authorClicked = authorClicked)
        if (isFirstMessageByAuthor) {
            // Last bubble before next author
            Spacer(modifier = Modifier.height(8.dp))
        } else {
            // Between bubbles
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

private val ChatBubbleShape = RoundedCornerShape(3.dp, 20.dp, 20.dp, 20.dp)

@Composable
fun ChatItemBubble(message: Message, isUserMe: Boolean, authorClicked: (String) -> Unit) {
    val backgroundBubbleColor = if (isUserMe) {
        Color.Green
    } else {
        Color.LightGray
    }
    
    Column {
        Surface(
            color = backgroundBubbleColor,
            shape = ChatBubbleShape
        ) {
            Text(
                text = message.content,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier
                    .padding(10.dp)
            )
        }
    }
}

@Composable
fun AuthorNameTimestamp(msg:Message) {
    Row(modifier = Modifier.semantics(mergeDescendants = true) {}) {
        Text(
            text = msg.userId,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.alignBy(LastBaseline),
            color = MaterialTheme.colors.onSurface
        )
    }
}
