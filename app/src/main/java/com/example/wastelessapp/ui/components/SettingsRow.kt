package com.example.wastelessapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class NotificationSettingsItem(
    val daysBeforeExpiration: Int,
    var isChecked: MutableState<Boolean> = mutableStateOf(false)


    ) {
    fun rowMessage(): String {

        return when {
            daysBeforeExpiration > 1 -> "$daysBeforeExpiration Days"
            daysBeforeExpiration == 1 -> "1 Day"
            daysBeforeExpiration == 0 -> "On the day of expiration"
            else -> "Error "
        }
    }
    fun checkOrUncheck(): Unit {
        isChecked.value = !isChecked.value
        println("isChecked: $isChecked")
    }
}


@Composable
fun NotificationSettingsRow(notificationSettingItem: NotificationSettingsItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .bottomBorder()
            .padding(10.dp),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = Icons.Outlined.Notifications,
            contentDescription = "Notification",

            modifier = Modifier.size(48.dp)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = notificationSettingItem.rowMessage(),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal, // slight boldness
                modifier = Modifier
            )
        }
        Spacer(Modifier.weight(1f))
        ToggleIconButton(isChecked = notificationSettingItem.isChecked.value, onCheckedChange = notificationSettingItem::checkOrUncheck)




        }


    }



@Composable
fun ToggleIconButton(isChecked: Boolean, onCheckedChange: () -> Unit) {
    val icon = if (isChecked) Icons.Default.Check else Icons.Default.Close
    val backgroundColor = if (isChecked) Color.Green.copy(alpha = 0.5f) else Color.Red.copy(alpha = 0.5f)

    IconButton(
        onClick = onCheckedChange,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Check",
            tint = Color.White,
            modifier = Modifier
                .background(backgroundColor, shape = RoundedCornerShape(8.dp)),
                )

    }
}
