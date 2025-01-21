package com.example.wastelessapp.database.entities.inventory_item

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeInventoryItemDao : InventoryItemDao {
    private val inventoryItems = mutableListOf<InventoryItem>()

    override suspend fun upsertInventoryItem(inventoryItem: InventoryItem) {
        inventoryItems.removeIf { it.id == inventoryItem.id }
        inventoryItems.add(inventoryItem)
    }

    override suspend fun deleteInventoryItem(inventoryItem: InventoryItem) {
        inventoryItems.remove(inventoryItem)
    }

    override fun getActiveInventoryItemsByName(state: String): Flow<List<InventoryItem>> {
        return flowOf(inventoryItems.filter { it.state.name == "ACTIVE" }.sortedBy { it.product })
    }

    override fun getActiveInventoryItemsByExpirationDate(state: String): Flow<List<InventoryItem>> {
        return flowOf(inventoryItems.filter { it.state.name == "ACTIVE" }.sortedBy { it.expirationDate })
    }

    override fun getAllInventoryItems(): Flow<List<InventoryItem>> {
        return flowOf(inventoryItems)
    }

    override fun getInventoryItemsByAmount(): Flow<List<InventoryItem>> {
        return flowOf(inventoryItems.sortedWith(compareBy({ it.itemUnit }, { it.amount })))
    }

    override suspend fun updateItemState(id: Int, newState: ItemState) {
        inventoryItems.find { it.id == id }?.state = newState
    }

    //Next methods are not needed for testing
    override suspend fun countItemsInLast30DaysByState(state: ItemState): Int {
        return 0
    }

    override suspend fun countAllItemsByState(state: ItemState): Int {
        return 0
    }

    override suspend fun countTotalItemsInLast30Days(): Int {
        return 0
    }

    override suspend fun countTotalItems(): Int {
        return 0
    }

    override suspend fun getMonthlyStatistics(): List<InventoryItemMonthlyStatistic> {
        return emptyList()
    }

    override suspend fun getMoneyLostLastMonth(state: ItemState): Float {
        return 0f
    }

    override suspend fun getMoneyLostAllTime(state: ItemState): Float {
        return 0f
    }

    override suspend fun countItemsExpiringSoon(state: ItemState): Int {
        return 0
    }

    override suspend fun countExpiredActiveItems(state: ItemState): Int {
        return 0
    }


}
