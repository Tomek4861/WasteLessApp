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
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


enum class FoodUnit {
    PCS,
    G,
    MILLILITER
}


data class ShoppingItem(
    val id: Int,
    val name: String,
    val quantity: Int,
    val unit: FoodUnit,
    val isChecked: Boolean = false,
    val category: String = determineCategory(name),
    val icon: ImageVector = determineIcon(category)
) {
    fun getQtyString(): String {
        return when (unit) {
            FoodUnit.PCS -> "$quantity pcs"
            FoodUnit.G -> "${quantity}g"
            FoodUnit.MILLILITER -> "${quantity}ml"
        }
    }

    companion object {
        private fun determineCategory(name: String): String {
            // TODO: implement using some db
            return when {
                name.contains("apple", ignoreCase = true) -> "Fruits"
                else -> "Others"
            }
        }

        private fun determineIcon(category: String): ImageVector {
            // TODO: implement using some db
            return when (category) {
                "Fruits" -> Icons.Filled.Face
                else -> Icons.Default.Warning
            }
        }
    }
}

@Composable
fun ShoppingListItem(item: ShoppingItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .bottomBorder(),

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
                textAlign = TextAlign.Center,
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

        IconButton(
            onClick = { /* TODO*/ },
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
            onClick = {  /* TODO*/ },
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