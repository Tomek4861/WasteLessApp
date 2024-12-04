package com.example.wastelessapp.database.entities.inventory_item

import java.sql.Date
import java.time.LocalDate

data class InventoryItemState(
    val inventoryItems: List<InventoryItem> = emptyList(),
    val product: Int = 0, //TODO check is can be null
    val itemUnit: ItemUnit = ItemUnit.PIECES,
    val amount: Float = 0f,
    val expirationDate: Date = Date.valueOf(LocalDate.now().toString()),
    val price: Float = 0f,
    val isAddingItem: Boolean = false,
    val sortType: SortType = SortType.EXPIRATION_DATE
)