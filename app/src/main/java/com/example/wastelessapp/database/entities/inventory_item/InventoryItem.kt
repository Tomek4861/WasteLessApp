package com.example.wastelessapp.database.entities.inventory_item

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.time.LocalDate

@Entity
data class InventoryItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var product: String,
    var itemUnit: ItemUnit,
    var amount: Float,
    var dateAdded: String = Date.valueOf(LocalDate.now().toString()).toString(),
    var expirationDate: String,
    var price: Float?,
    var state: ItemState = ItemState.ACTIVE,
    var iconResId: Int
)