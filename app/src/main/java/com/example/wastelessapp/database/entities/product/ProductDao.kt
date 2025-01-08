package com.example.wastelessapp.database.entities.product

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Upsert
    suspend fun upsertProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Transaction
    @Query("SELECT * FROM product ORDER BY name ASC")
    fun getProductsByName(): Flow<List<Product>>

    @Query("SELECT iconResId FROM Product WHERE name = :productName LIMIT 1")
    suspend fun getIconResIdByProductName(productName: String): Int?
}