package com.example.wastelessapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.wastelessapp.database.entities.inventory_item.InventoryItem
import com.example.wastelessapp.database.entities.inventory_item.InventoryItemDao
import com.example.wastelessapp.database.entities.inventory_item.ItemStateConverters
import com.example.wastelessapp.database.entities.inventory_item.ItemUnitConverters
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
    version = 1
)
@TypeConverters(ItemStateConverters::class, ItemUnitConverters::class)
abstract class WasteLessAppDatabase : RoomDatabase() {
    abstract val inventoryItemDao: InventoryItemDao
    abstract val ProductDao: ProductDao
    abstract val ShoppingCartDao: ShoppingCartDao
}