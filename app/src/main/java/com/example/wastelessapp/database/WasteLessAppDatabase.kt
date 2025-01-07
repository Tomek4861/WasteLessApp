package com.example.wastelessapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.wastelessapp.database.entities.inventory_item.InventoryItem
import com.example.wastelessapp.database.entities.inventory_item.InventoryItemDao
import com.example.wastelessapp.database.entities.inventory_item.ItemConverters
import com.example.wastelessapp.database.entities.product.Product
import com.example.wastelessapp.database.entities.product.ProductDao
import com.example.wastelessapp.database.entities.shopping_cart.ShoppingCartDao
import com.example.wastelessapp.database.entities.shopping_cart.ShoppingCartItem

@Database(
    entities = [
        InventoryItem::class,
        Product::class,
        ShoppingCartItem::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(ItemConverters::class)
abstract class WasteLessAppDatabase : RoomDatabase() {
    abstract val inventoryItemDao: InventoryItemDao
    abstract val productDao: ProductDao
    abstract val shoppingCartDao: ShoppingCartDao
}