package com.example.wastelessapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wastelessapp.ui.components.CustomTopAppBar
import com.example.wastelessapp.ui.components.FoodInventoryItem
import com.example.wastelessapp.ui.components.FoodItem
import com.example.wastelessapp.ui.components.FoodUnit
import com.example.wastelessapp.ui.components.PrimaryButton
import com.example.wastelessapp.ui.components.SecondaryButton
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
object StatisticsScreen

@Preview
@Composable
fun StatisticsScreen() {
    Column(){

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                //.verticalScroll(rememberScrollState())
        ){

            Text(
                "Your Statistics",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Last 30 days:",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            StatisticsRow("Items saved", "98")
            HorizontalDivider(thickness = 2.dp)
            StatisticsRow("Items lost", "2")
            HorizontalDivider(thickness = 2.dp)
            StatisticsRow("Percentage of items lost", "2.0%")

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "All time:",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            StatisticsRow("Items saved", "1576")
            HorizontalDivider(thickness = 2.dp)
            StatisticsRow("Items lost", "113")
            HorizontalDivider(thickness = 2.dp)
            StatisticsRow("Percentage of items lost", "6.7%")

        }
    }
}

@Composable
fun StatisticsRow(
    text: String,
    value: String
){
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = text,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )

        Text(
            text = value,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
    }
}