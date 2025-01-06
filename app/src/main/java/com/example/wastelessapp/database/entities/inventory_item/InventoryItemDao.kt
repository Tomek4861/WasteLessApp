package com.example.wastelessapp.database.entities.inventory_item

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryItemDao {

    @Upsert
    suspend fun upsertInventoryItem(inventoryItem: InventoryItem)

    @Delete
    suspend fun deleteInventoryItem(inventoryItem: InventoryItem)

    @Transaction
    @Query("SELECT * FROM InventoryItem WHERE state = :state ORDER BY product ASC")
    fun getActiveInventoryItemsByName(state: String = "ACTIVE"): Flow<List<InventoryItem>>

    @Transaction
    @Query("SELECT * FROM InventoryItem WHERE state = :state ORDER BY expirationDate ASC")
    fun getActiveInventoryItemsByExpirationDate(state: String = "ACTIVE"): Flow<List<InventoryItem>>

    @Transaction
    @Query("SELECT * FROM inventoryitem")
    fun getAllInventoryItems(): Flow<List<InventoryItem>>

    @Transaction
    @Query("SELECT * FROM inventoryitem ORDER BY itemUnit, amount ASC")
    fun getInventoryItemsByAmount(): Flow<List<InventoryItem>>

    @Transaction
    @Query("UPDATE InventoryItem SET state = :newState WHERE id = :id")
    suspend fun updateItemState(id: Int, newState: ItemState)

    @Query("""
        SELECT COUNT(*) 
        FROM InventoryItem 
        WHERE dateAdded >= strftime('%Y-%m-%d', 'now', '-30 days') 
        AND state = :state
    """)
    suspend fun countItemsInLast30DaysByState(state: ItemState): Int

    @Query("SELECT COUNT(*) FROM InventoryItem WHERE state = :state")
    suspend fun countAllItemsByState(state: ItemState): Int

    @Query("""
        SELECT COUNT(*) 
        FROM InventoryItem 
        WHERE dateAdded >= strftime('%Y-%m-%d', 'now', '-30 days')
    """)
    suspend fun countTotalItemsInLast30Days(): Int

    @Query("SELECT COUNT(*) FROM InventoryItem")
    suspend fun countTotalItems(): Int

    @Query("""
        SELECT 
            strftime('%Y-%m', dateAdded) AS month,
            state,
            COUNT(*) AS count
        FROM InventoryItem
        WHERE dateAdded >= strftime('%Y-%m-%d', 'now', '-12 months')
        GROUP BY month, state
        ORDER BY month ASC
    """)
    suspend fun getMonthlyStatistics(): List<InventoryItemMonthlyStatistic>

}