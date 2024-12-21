package com.example.wastelessapp.database.entities.inventory_item

import java.sql.Date

sealed interface InventoryItemEvent {
    data object SaveInventoryItem : InventoryItemEvent
    data class SetProduct(val product: String) : InventoryItemEvent
    data class SetUnit(val itemUnit: ItemUnit) : InventoryItemEvent
    data class SetAmount(val amount: Float) : InventoryItemEvent
    data class SetExpirationDate(val expirationDate: Date) : InventoryItemEvent
    data class SetPrice(val price: Float) : InventoryItemEvent
    data object ShowDialog : InventoryItemEvent
    data object HideDialog : InventoryItemEvent
    data class SortProducts(val sortType: SortType) : InventoryItemEvent
    data class DeleteInventoryItem(val inventoryItem: InventoryItem) : InventoryItemEvent
    data class UpdateItemState(val inventoryItem: InventoryItem) : InventoryItemEvent
}