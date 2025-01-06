package com.example.wastelessapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.wastelessapp.database.entities.inventory_item.ItemUnit


enum class FoodUnit {
    PCS,
    GRAM,
    MILLILITER
}

open class BaseItem(
    open val id: Int,
    open val name: String,
    open val quantity: Float,
    open val unit: ItemUnit,
    open val category: String = determineCategory(name),
    open val icon: ImageVector = determineIcon(category)

) {
    fun getQtyString(): String {

        return when (unit) {
            ItemUnit.GRAMS -> "${quantity}g"
            ItemUnit.KILOGRAMS -> "${quantity}kg"
            ItemUnit.LITERS -> "${quantity}l"
            ItemUnit.PIECES -> "$quantity pcs"
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
