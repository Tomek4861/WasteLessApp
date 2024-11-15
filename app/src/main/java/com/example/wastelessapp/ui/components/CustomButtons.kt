package com.example.wastelessapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape




@Composable
fun CustomButton(
    text: String,
    onClick: () -> kotlin.Unit, // no args -> void
    backgroundColor: Color,
    contentColor: Color,
    borderColor: Color? = null, // optional
    width: Dp = 200.dp,
    fontSize: TextUnit = 18.sp

) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(width)
            .let { if (borderColor != null) it.border(2.dp, borderColor, RoundedCornerShape(8.dp)) else it }
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            color = contentColor
        )
    }
}

@Composable
fun BlackButton(text: String, onClick: () -> kotlin.Unit, width: Dp = 200.dp, fontSize: TextUnit = 18.sp) {
    CustomButton(
        text = text,
        onClick = onClick,
        backgroundColor = Color.Black,
        contentColor = Color.White,
        width = width
    )
}

@Composable
fun WhiteButton(text: String, onClick: () -> kotlin.Unit, width: Dp = 200.dp, fontSize: TextUnit = 18.sp) {
    CustomButton(
        text = text,
        onClick = onClick,
        backgroundColor = Color.White,
        contentColor = Color.Black,
        borderColor = Color.Black,
        width = width

    )
}
