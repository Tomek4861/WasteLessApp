package com.example.wastelessapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.wastelessapp.database.entities.inventory_item.InventoryItem
import com.example.wastelessapp.database.entities.inventory_item.InventoryItemDao
import com.example.wastelessapp.database.entities.inventory_item.ItemStateConverters
import com.example.wastelessapp.database.entities.inventory_item.ItemUnitConverters

@Database(
    entities = [InventoryItem::class],
    version = 1
)
@TypeConverters(ItemStateConverters::class, ItemUnitConverters::class)
abstract class WasteLessAppDatabase: RoomDatabase() {
    abstract val inventoryItemDao: InventoryItemDao
}