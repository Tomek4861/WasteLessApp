package com.example.wastelessapp.database.entities.inventory_item

data class InventoryItemMonthlyStatistic(
    val month: String,
    val state: String,
    val count: Int
)