package com.example.wastelessapp.database.rooms.product

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

interface ProductDao {

    @Upsert
    suspend fun upsertProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("SELECT * FROM product ORDER BY name ASC")
    fun getProductsByName(): Flow<List<Product>>

    @Query("SELECT * FROM product ORDER BY expirationDate ASC")
    fun getProductsByExpirationDate(): Flow<List<Product>>

    @Query("SELECT * FROM product ORDER BY unit, amount ASC")
    fun getProductsByAmount(): Flow<List<Product>>
}