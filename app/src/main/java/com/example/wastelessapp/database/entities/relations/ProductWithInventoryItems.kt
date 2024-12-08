package com.example.wastelessapp.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.wastelessapp.database.entities.inventory_item.InventoryItem
import com.example.wastelessapp.database.entities.product.Product

data class ProductWithInventoryItems (
    @Embedded
    val product: Product,
    @Relation(
        parentColumn = "name",
        entityColumn = "product"
    )
    val inventoryItems: List<InventoryItem>
)