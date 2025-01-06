package com.example.wastelessapp.database.entities.product

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

interface ProductDao {

    @Upsert
    suspend fun upsertProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    fun getProducts(): Flow<List<Product>>

    @Transaction
    @Query("SELECT * FROM product ORDER BY name ASC")
    fun getProductsByName(): Flow<List<Product>>
}