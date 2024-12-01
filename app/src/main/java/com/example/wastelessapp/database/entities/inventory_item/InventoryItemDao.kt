package com.example.wastelessapp.database.entities.inventory_item

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

interface InventoryItemDao {

    @Upsert
    suspend fun upsertProduct(inventoryItem: InventoryItem)

    @Delete
    suspend fun deleteProduct(inventoryItem: InventoryItem)

    @Query("SELECT * FROM InventoryItem WHERE state = :state ORDER BY product ASC") //TODO add active items filter
    fun getProductsByName(state: String = "ACTIVE"): Flow<List<InventoryItem>>

    @Query("SELECT * FROM inventoryitem ORDER BY expirationDate ASC")
    fun getProductsByExpirationDate(): Flow<List<InventoryItem>>

    @Query("SELECT * FROM inventoryitem ORDER BY itemUnit, amount ASC")
    fun getProductsByAmount(): Flow<List<InventoryItem>>
}