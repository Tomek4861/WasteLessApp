package com.example.wastelessapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wastelessapp.database.entities.inventory_item.ItemUnit
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit


data class FoodItem(
    override val id: Int,
    override val name: String,
    override val quantity: Float,
    override val unit: ItemUnit,
    val price: Float?,
    val expiryDate: LocalDateTime,
    val purchaseDate: LocalDateTime,

    ) : BaseItem(id, name, quantity, unit) {
    fun getDaysLeft(): Int {
        return ChronoUnit.DAYS.between(LocalDateTime.now().toLocalDate(), expiryDate.toLocalDate())
            .toInt()
    }

    fun isExpired(): Boolean {
        return getDaysLeft() > 0
    }

    fun expireMessage(): String {
        val daysLeft: Int = getDaysLeft()

        return when {
            daysLeft > 1 -> "Expires in $daysLeft days"
            daysLeft == 1 -> "Expires in 1 day"
            daysLeft == 0 -> "Expires Today!"
            daysLeft == -1 -> "Expired 1 day ago"
            else -> "Expired ${-daysLeft} days ago"
        }
    }

    fun getBackgroundBasedOnExpiry(): Brush {
        val daysLeft: Int = getDaysLeft()
        val gradientIntensity: Float = 0.14f
        return Brush.horizontalGradient(
            colors = when {
                daysLeft < 0 -> listOf(Color.Red.copy(alpha = gradientIntensity), Color.Transparent)
                daysLeft in 0..1 -> listOf(Color.Yellow.copy(alpha = gradientIntensity), Color.Transparent)
                else -> listOf(Color.Transparent, Color.Transparent)
            }
        )

    }
}

@Composable
fun FoodInventoryItem(item: FoodItem, onCheck: (FoodItem) -> Unit, onDelete: (FoodItem) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .bottomBorder()
            .background(item.getBackgroundBasedOnExpiry())
            .padding(4.dp),
        //,


        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start

    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.category,

            modifier = Modifier.size(48.dp)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = item.name,
                fontSize = 20.sp,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Medium, // slight boldness
                modifier = Modifier
            )
            Text(
                text = item.getQtyString(),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,

                modifier = Modifier
                    .alpha(0.5f)   // changing opacity

            )
        }
        Spacer(Modifier.weight(1f))
        Text(
            text = item.expireMessage(),
            fontSize = 18.sp,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Medium, // slight boldness
            softWrap = true,
            modifier = Modifier.width(80.dp)
        )

        IconButton(
            onClick = { onCheck(item) },
            modifier = Modifier.size(48.dp) // default size
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Check",
                tint = Color.White,
                modifier = Modifier.background(
                    Color.Black.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(8.dp)
                )
            )
        }


        IconButton(
            onClick = { onDelete(item) },
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.Red,
                modifier = Modifier.background(
                    Color.Red.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                )
            )

        }


    }


}