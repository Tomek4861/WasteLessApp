package com.example.wastelessapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
    fontSize: TextUnit = 18.sp,
    icon: ImageVector? = null

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
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor
            )
        }
    }
}


@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    width: Dp = 200.dp,
    fontSize: TextUnit = 18.sp,
    icon: ImageVector? = null
) {
    CustomButton(
        text = text,
        onClick = onClick,
        fontSize = fontSize,
        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        width = width,
        icon = icon
    )
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    width: Dp = 200.dp,
    fontSize: TextUnit = 18.sp,
    icon: ImageVector? = null
) {
    CustomButton(
        text = text,
        onClick = onClick,
        fontSize = fontSize,
        backgroundColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary,
        width = width,
        icon = icon

    )
}


@Composable
fun UncheckedButton(
    text: String,
    onClick: () -> Unit,
    width: Dp = 200.dp,
    fontSize: TextUnit = 18.sp,
    icon: ImageVector? = null
) {
    CustomButton(
        text = text,
        onClick = onClick,
        fontSize = fontSize,
        backgroundColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f),
        borderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
        width = width,
        icon = icon
    )
}

@Composable
fun ButtonRow(
    buttons: List<String>,
    currentlySelected: String,
    buttonWidth: Dp = 90.dp,
    fontSize: TextUnit = 14.sp,
    onSelected: (String) -> Unit
) {


    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {

        buttons.forEach { buttonText ->
            if (buttonText == currentlySelected) {
                SecondaryButton(
                    text = buttonText,
                    onClick = { /* Not needed here I guess */ },
                    width = buttonWidth,
                    fontSize = fontSize
                )
            } else {
                UncheckedButton(
                    text = buttonText,
                    onClick = {
                        onSelected(buttonText)
                    },
                    width = buttonWidth,
                    fontSize = fontSize
                )
            }
        }
    }
}

@Composable
fun SquareIconButton(
    onClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    icon: ImageVector = Icons.Default.Add,
    size: Dp = 56.dp,
    iconSize: Dp = 32.dp
) {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Add",
            tint = contentColor,
            modifier = Modifier.size(iconSize)
        )
    }
}
