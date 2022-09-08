package com.example.taboada.rtdbexample3.screens.chats

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taboada.rtdbexample3.model.ChatDetails
import com.example.taboada.rtdbexample3.R.drawable as AppIcons

@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewChatDetailsItem() {
    ChatDetailsItem(chatDetails = ChatDetails("Fooooo", members = mutableMapOf("me" to "foo")), onClickAction = {})
}


@Composable
@ExperimentalMaterialApi
fun ChatDetailsItem(
    chatDetails: ChatDetails,
    onClickAction: () -> Unit
) {
    Card(
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 8.dp),
        onClick = onClickAction
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Image(
                modifier = Modifier
                    /*.clickable(onClick = { onAuthorClick(msg.author) })*/
                    .padding(horizontal = 16.dp)
                    .size(42.dp)
                    .border(1.5.dp, MaterialTheme.colors.primary, CircleShape)
                    .border(3.dp, MaterialTheme.colors.surface, CircleShape)
                    .clip(CircleShape)
                /*.align(Alignment.Top)*/,
                painter = painterResource(id = AppIcons.daisy),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(text = chatDetails.chatID, style = MaterialTheme.typography.subtitle2)
                    Text(text = "${chatDetails.members.size} members", fontSize = 12.sp)
            }
        }
    }
}
