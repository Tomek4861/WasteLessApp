package com.example.wastelessapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector


enum class FoodUnit {
    PCS,
    GRAM,
    MILLILITER,
    KILOGRAM
}

open class BaseItem(
    open val id: Int,
    open val name: String,
    open val quantity: Int,
    open val unit: FoodUnit,
    open val category: String = determineCategory(name),
    open val icon: ImageVector = determineIcon(category)

) {
    fun getQtyString(): String {
        return when (unit) {
            FoodUnit.PCS -> "$quantity pcs"
            FoodUnit.GRAM -> "${quantity}g"
            FoodUnit.MILLILITER -> "${quantity}ml"
            FoodUnit.KILOGRAM -> "${quantity}kg"
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

