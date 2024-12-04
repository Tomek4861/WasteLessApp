package com.example.wastelessapp.database.relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.example.wastelessapp.database.entities.inventory_item.InventoryItem
import com.example.wastelessapp.database.entities.product.Product

data class ProductWithInventoryItems (
    @Embedded
    val product: Product,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val inventoryItems: List<InventoryItem>
)