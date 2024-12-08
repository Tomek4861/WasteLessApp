package com.example.wastelessapp.database.entities.inventory_item

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

interface InventoryItemDao {

    @Upsert
    suspend fun upsertInventoryItem(inventoryItem: InventoryItem)

    @Delete
    suspend fun deleteInventoryItem(inventoryItem: InventoryItem)

    @Transaction
    @Query("SELECT * FROM InventoryItem WHERE state = :state ORDER BY product ASC")
    fun getActiveInventoryItemsByName(state: String = "ACTIVE"): Flow<List<InventoryItem>>

    @Transaction
    @Query("SELECT * FROM inventoryitem ORDER BY expirationDate ASC")
    fun getInventoryItemsByExpirationDate(): Flow<List<InventoryItem>>

    @Transaction
    @Query("SELECT * FROM inventoryitem ORDER BY itemUnit, amount ASC")
    fun getInventoryItemsByAmount(): Flow<List<InventoryItem>>
}