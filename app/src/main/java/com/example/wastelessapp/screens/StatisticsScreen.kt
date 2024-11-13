package com.example.wastelessapp.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import kotlinx.serialization.Serializable

@Serializable
object StatisticsScreen

@Composable
fun StatisticsScreen() {
    Text(
        "Statistics Screen",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}