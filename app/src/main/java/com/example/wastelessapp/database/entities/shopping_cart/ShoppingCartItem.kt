package com.example.wastelessapp.database.entities.shopping_cart

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wastelessapp.database.entities.inventory_item.ItemUnit

@Entity
data class ShoppingCartItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val product: String,
    var itemUnit: ItemUnit,
    var amount: Float,
    var iconResId: Int
)
