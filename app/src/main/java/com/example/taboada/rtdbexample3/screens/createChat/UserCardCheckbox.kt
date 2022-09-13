package com.example.taboada.rtdbexample3.screens.createChat

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taboada.rtdbexample3.model.ChatUser
import com.example.taboada.rtdbexample3.R.drawable as AppIcons


@ExperimentalMaterialApi
@Composable
fun UserCardCheckbox(
    user: ChatUser,
    onClickAction: (Boolean,ChatUser) -> Unit
) {
    val checkedState = remember { mutableStateOf(false) }
    Card(
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.padding(1.dp, 1.dp, 1.dp, 1.dp),
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Checkbox(checked = checkedState.value, onCheckedChange = { checkedState.value = it
                onClickAction(it, user)
            })
            Image(
                modifier = Modifier
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
                Text(text = user.displayName, style = MaterialTheme.typography.subtitle2)
                Text(text = "${user.email}", fontSize = 12.sp)
            }
        }
    }
}
