package com.example.wastelessapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wastelessapp.R
import com.example.wastelessapp.ui.components.ButtonRow
import com.example.wastelessapp.ui.components.CustomTopAppBar
import com.example.wastelessapp.ui.components.NotificationSettingsItem
import com.example.wastelessapp.ui.components.NotificationSettingsRow
import com.example.wastelessapp.ui.components.PrimaryButton
import com.example.wastelessapp.ui.components.SecondaryButton
import com.example.wastelessapp.ui.components.UncheckedButton
import com.example.wastelessapp.ui.components.bottomBorder
import kotlinx.serialization.Serializable

@Serializable
object SettingsScreen

@Preview
@Composable
fun SettingsScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxSize()


    )
    {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {

//            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Notification Settings",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,

                )
            for (i in 3 downTo 0) {
                NotificationSettingsRow(notificationSettingItem=
                NotificationSettingsItem(daysBeforeExpiration=i)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text="Notification Sound",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,

                )
            ButtonRow(listOf("Meow", "Bell", "Clap", "Tick"), "Meow")

            Text(
                text="Choose a sound",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
                modifier = Modifier.alpha(0.5f)

                )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text="App Info",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row (horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .bottomBorder()
                    .padding(0.dp, 10.dp, 10.dp, 10.dp) // no left padding

            ) {

                AppLogo()
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                ) {
                    Text(text="Version",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(8.dp)

                    )
                    Text(text="1.0",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(8.dp)

                    )
                }
                Spacer(modifier = Modifier.height(12.dp))


            }



        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {
            PrimaryButton(
                text = "Reset App",
                onClick = { /*TODO*/ },
                width = 220.dp
            )}



    }
}

@Composable
fun AppLogo(){
    Image(
        painter = painterResource(id = R.drawable.app_logo),
        contentDescription = "App Logo",
        modifier = Modifier.size(100.dp)
    )

}


