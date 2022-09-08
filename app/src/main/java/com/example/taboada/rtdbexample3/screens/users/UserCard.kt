package com.example.taboada.rtdbexample3.screens.users

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
import com.example.taboada.rtdbexample3.model.ChatUser
import com.example.taboada.rtdbexample3.R.drawable as AppIcons

@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewChatDetailsItem() {
    UserCard(
        user = ChatUser(
            email = "tabo.odricar@gmail.com",
            displayName = "Ricardo Taboada",
            userID = "U5b02iuWfQOAQj1uAzdoBi6p8Qr2"
        ),
        onClickAction = {}
    )
}


@Composable
@ExperimentalMaterialApi
fun UserCard(
    user: ChatUser,
    onClickAction: (Boolean) -> Unit,
    isSelected: Boolean = false
) {
    Card(
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.padding(1.dp, 1.dp, 1.dp, 1.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Checkbox(checked = isSelected, onCheckedChange = onClickAction)
            Image(
                modifier = Modifier
                    /*.clickable(onClick = { onAuthorClick(msg.author) })*/
                    .padding(horizontal = 16.dp)
                    .size(42.dp)
                    .border(1.5.dp, MaterialTheme.colors.primary, CircleShape)
                    .border(3.dp, MaterialTheme.colors.surface, CircleShape)
                    .clip(CircleShape)
                /*.align(Alignment.Top)*/,
                painter = painterResource(id = AppIcons.peach),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(text = user.displayName, style = MaterialTheme.typography.subtitle2)
                Text(text = "${user.email}", fontSize = 12.sp)
            }
        }
    }
}
