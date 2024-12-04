package com.example.wastelessapp.database.entities.inventory_item

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.time.LocalDate

@Entity
data class InventoryItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var product: Int,
    var itemUnit: ItemUnit,
    var amount: Float,
    var dateAdded: Date = Date.valueOf(LocalDate.now().toString()),
    var expirationDate: Date,
    var price: Float?,
    var state: ItemState = ItemState.ACTIVE
)