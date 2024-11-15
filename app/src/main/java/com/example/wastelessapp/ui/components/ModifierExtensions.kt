package com.example.wastelessapp.ui.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


fun Modifier.bottomBorder(
    color: Color = Color.Gray,
    strokeWidth: Dp = 1.dp
): Modifier = this.then(
    Modifier.drawBehind {
        val strokePx = strokeWidth.toPx()
        drawLine(
            color = color,
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            strokeWidth = strokePx
        )
    }
)
