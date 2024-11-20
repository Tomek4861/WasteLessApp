package com.example.wastelessapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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


@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit, // no args -> void
    backgroundColor: Color,
    contentColor: Color,
    borderColor: Color? = Color.Black,
    width: Dp = 200.dp,
    fontSize: TextUnit = 18.sp

) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),


        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, borderColor ?: Color.Transparent),

        modifier = Modifier
            .width(width)
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
        )
    }
}


@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    width: Dp = 200.dp,
    fontSize: TextUnit = 18.sp
) {
    CustomButton(
        text = text,
        onClick = onClick,
        fontSize = fontSize,
        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        width = width
    )
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    width: Dp = 200.dp,
    fontSize: TextUnit = 18.sp
) {
    CustomButton(
        text = text,
        onClick = onClick,
        fontSize = fontSize,
        backgroundColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary,
        width = width

    )
}


@Composable
fun UncheckedButton(
    text: String,
    onClick: () -> Unit,
    width: Dp = 200.dp,
    fontSize: TextUnit = 18.sp
) {
    CustomButton(
        text = text,
        onClick = onClick,
        fontSize = fontSize,
        backgroundColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        borderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
        width = width
    )
}
