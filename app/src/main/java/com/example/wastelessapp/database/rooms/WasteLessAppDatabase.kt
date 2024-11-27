package com.example.wastelessapp.database.rooms

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wastelessapp.database.rooms.product.Product
import com.example.wastelessapp.database.rooms.product.ProductDao

@Database(
    entities = [Product::class],
    version = 1
)
abstract class WasteLessAppDatabase: RoomDatabase() {
    abstract val productDao: ProductDao
}