package com.example.wastelessapp.database.entities.inventory_item

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wastelessapp.R
import com.example.wastelessapp.database.WasteLessAppDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.sql.Date
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class InventoryItemDaoTest {

    private lateinit var database: WasteLessAppDatabase
    private lateinit var inventoryItemDao: InventoryItemDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            WasteLessAppDatabase::class.java
        ).build()
        inventoryItemDao = database.inventoryItemDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun upsertInventoryItem_insertsNewItem() = runTest {
        val inventoryItem = InventoryItem(
            id = 1,
            product = "Milk",
            itemUnit = ItemUnit.LITERS,
            amount = 1.5f,
            expirationDate = Date.valueOf(LocalDate.now().plusDays(5).toString()).toString(),
            price = 3.99f,
            state = ItemState.ACTIVE,
            iconResId = R.drawable.milk_bottle_icon
        )

        inventoryItemDao.upsertInventoryItem(inventoryItem)

        val items = inventoryItemDao.getAllInventoryItems().first()
        assertEquals(1, items.size)
        assertEquals("Milk", items[0].product)
    }

    @Test
    fun upsertInventoryItem_updatesExistingItem() = runTest {
        val inventoryItem = InventoryItem(
            id = 1,
            product = "Milk",
            itemUnit = ItemUnit.LITERS,
            amount = 1.5f,
            expirationDate = Date.valueOf(LocalDate.now().plusDays(5).toString()).toString(),
            price = 3.99f,
            state = ItemState.ACTIVE,
            iconResId = R.drawable.milk_bottle_icon
        )

        inventoryItemDao.upsertInventoryItem(inventoryItem)

        val updatedItem = inventoryItem.copy(price = 4.99f)
        inventoryItemDao.upsertInventoryItem(updatedItem)

        val items = inventoryItemDao.getAllInventoryItems().first()
        assertEquals(1, items.size)
        items[0].price?.let { assertEquals(4.99f, it, 0.01f) }
    }

    @Test
    fun deleteInventoryItem_removesItem() = runTest {
        val inventoryItem = InventoryItem(
            id = 1,
            product = "Milk",
            itemUnit = ItemUnit.LITERS,
            amount = 1.5f,
            expirationDate = Date.valueOf(LocalDate.now().plusDays(5).toString()).toString(),
            price = 3.99f,
            state = ItemState.ACTIVE,
            iconResId = R.drawable.milk_bottle_icon
        )

        inventoryItemDao.upsertInventoryItem(inventoryItem)
        inventoryItemDao.deleteInventoryItem(inventoryItem)

        val items = inventoryItemDao.getAllInventoryItems().first()
        assertTrue(items.isEmpty())
    }

    @Test
    fun getActiveInventoryItemsByName_returnsSortedItems() = runTest {
        val items = listOf(
            InventoryItem(
                id = 1,
                product = "Banana",
                itemUnit = ItemUnit.PIECES,
                amount = 6.0f,
                expirationDate = Date.valueOf(LocalDate.now().plusDays(3).toString()).toString(),
                price = 2.0f,
                state = ItemState.ACTIVE,
                iconResId = R.drawable.banana_fruit_icon
            ),
            InventoryItem(
                id = 2,
                product = "Apple",
                itemUnit = ItemUnit.KILOGRAMS,
                amount = 2.0f,
                expirationDate = Date.valueOf(LocalDate.now().plusDays(5).toString()).toString(),
                price = 5.0f,
                state = ItemState.ACTIVE,
                iconResId = R.drawable.apple_fruits_icon
            )
        )

        items.forEach { inventoryItemDao.upsertInventoryItem(it) }

        val result = inventoryItemDao.getActiveInventoryItemsByName().first()

        assertEquals(2, result.size)
        assertEquals("Apple", result[0].product)
        assertEquals("Banana", result[1].product)
    }

    @Test
    fun getActiveInventoryItemsByExpirationDate_returnsSortedItems() = runTest {
        val items = listOf(
            InventoryItem(
                id = 1,
                product = "Milk",
                itemUnit = ItemUnit.LITERS,
                amount = 1.5f,
                expirationDate = Date.valueOf(LocalDate.now().plusDays(3).toString()).toString(),
                price = 3.5f,
                state = ItemState.ACTIVE,
                iconResId = R.drawable.milk_bottle_icon
            ),
            InventoryItem(
                id = 2,
                product = "Apple",
                itemUnit = ItemUnit.KILOGRAMS,
                amount = 2.0f,
                expirationDate = Date.valueOf(LocalDate.now().plusDays(1).toString()).toString(),
                price = 5.0f,
                state = ItemState.ACTIVE,
                iconResId = R.drawable.apple_fruits_icon
            )
        )

        items.forEach { inventoryItemDao.upsertInventoryItem(it) }

        val result = inventoryItemDao.getActiveInventoryItemsByExpirationDate().first()

        assertEquals(2, result.size)
        assertEquals("Apple", result[0].product)
        assertEquals("Milk", result[1].product)
    }

    @Test
    fun updateItemState_updatesState() = runTest {
        val inventoryItem = InventoryItem(
            id = 1,
            product = "Milk",
            itemUnit = ItemUnit.LITERS,
            amount = 1.5f,
            expirationDate = Date.valueOf(LocalDate.now().plusDays(5).toString()).toString(),
            price = 3.99f,
            state = ItemState.ACTIVE,
            iconResId = R.drawable.milk_bottle_icon
        )

        inventoryItemDao.upsertInventoryItem(inventoryItem)

        inventoryItemDao.updateItemState(1, ItemState.EXPIRED)

        val items = inventoryItemDao.getAllInventoryItems().first()
        assertEquals(ItemState.EXPIRED, items[0].state)
    }

    @Test
    fun countItemsInLast30DaysByState_returnsCorrectCount() = runTest {
        val items = listOf(
            InventoryItem(
                id = 1,
                product = "Milk",
                itemUnit = ItemUnit.LITERS,
                amount = 1.5f,
                expirationDate = Date.valueOf(LocalDate.now().plusDays(5).toString()).toString(),
                price = 3.99f,
                state = ItemState.ACTIVE,
                dateAdded = Date.valueOf(LocalDate.now().minusDays(10).toString()).toString(),
                iconResId = R.drawable.milk_bottle_icon
            ),
            InventoryItem(
                id = 2,
                product = "Apple",
                itemUnit = ItemUnit.KILOGRAMS,
                amount = 2.0f,
                expirationDate = Date.valueOf(LocalDate.now().plusDays(5).toString()).toString(),
                price = 5.0f,
                state = ItemState.EXPIRED,
                dateAdded = Date.valueOf(LocalDate.now().minusDays(40).toString()).toString(),
                iconResId = R.drawable.apple_fruits_icon
            )
        )

        items.forEach { inventoryItemDao.upsertInventoryItem(it) }

        val activeCount = inventoryItemDao.countItemsInLast30DaysByState(ItemState.ACTIVE)
        assertEquals(1, activeCount)
    }

    @Test
    fun getAllInventoryItems_returnsAllItems() = runTest {
        val items = listOf(
            InventoryItem(
                id = 1,
                product = "Milk",
                itemUnit = ItemUnit.LITERS,
                amount = 1.0f,
                expirationDate = Date.valueOf(LocalDate.now().plusDays(5).toString()).toString(),
                price = 3.5f,
                state = ItemState.ACTIVE,
                iconResId = R.drawable.milk_bottle_icon
            ),
            InventoryItem(
                id = 2,
                product = "Banana",
                itemUnit = ItemUnit.PIECES,
                amount = 6.0f,
                expirationDate = Date.valueOf(LocalDate.now().plusDays(10).toString()).toString(),
                price = 2.0f,
                state = ItemState.ACTIVE,
                iconResId = R.drawable.banana_fruit_icon
            )
        )

        items.forEach { inventoryItemDao.upsertInventoryItem(it) }

        val allItems = inventoryItemDao.getAllInventoryItems().first()

        assertEquals(2, allItems.size)
        assertTrue(allItems.any { it.product == "Milk" })
        assertTrue(allItems.any { it.product == "Banana" })
    }

    @Test
    fun countExpiredActiveItems_returnsCorrectCount() = runTest {
        val items = listOf(
            InventoryItem(
                id = 1,
                product = "Milk",
                itemUnit = ItemUnit.LITERS,
                amount = 1.0f,
                expirationDate = Date.valueOf(LocalDate.now().minusDays(1).toString()).toString(),
                price = 3.5f,
                state = ItemState.ACTIVE,
                iconResId = R.drawable.milk_bottle_icon
            ),
            InventoryItem(
                id = 2,
                product = "Banana",
                itemUnit = ItemUnit.PIECES,
                amount = 6.0f,
                expirationDate = Date.valueOf(LocalDate.now().plusDays(10).toString()).toString(),
                price = 2.0f,
                state = ItemState.ACTIVE,
                iconResId = R.drawable.banana_fruit_icon
            )
        )

        items.forEach { inventoryItemDao.upsertInventoryItem(it) }

        val expiredCount = inventoryItemDao.countExpiredActiveItems()

        assertEquals(1, expiredCount)
    }

    @Test
    fun countItemsExpiringSoon_returnsCorrectCount() = runTest {
        val items = listOf(
            InventoryItem(
                id = 1,
                product = "Milk",
                itemUnit = ItemUnit.LITERS,
                amount = 1.0f,
                expirationDate = Date.valueOf(LocalDate.now().toString()).toString(),
                price = 3.5f,
                state = ItemState.ACTIVE,
                iconResId = R.drawable.milk_bottle_icon
            ),
            InventoryItem(
                id = 2,
                product = "Banana",
                itemUnit = ItemUnit.PIECES,
                amount = 6.0f,
                expirationDate = Date.valueOf(LocalDate.now().plusDays(1).toString()).toString(),
                price = 2.0f,
                state = ItemState.ACTIVE,
                iconResId = R.drawable.banana_fruit_icon
            ),
            InventoryItem(
                id = 3,
                product = "Cheese",
                itemUnit = ItemUnit.GRAMS,
                amount = 500.0f,
                expirationDate = Date.valueOf(LocalDate.now().plusDays(5).toString()).toString(),
                price = 4.0f,
                state = ItemState.ACTIVE,
                iconResId = R.drawable.cheese_icon
            )
        )

        items.forEach { inventoryItemDao.upsertInventoryItem(it) }

        val expiringSoonCount = inventoryItemDao.countItemsExpiringSoon()

        assertEquals(2, expiringSoonCount)
    }

    @Test
    fun getMonthlyStatistics_returnsCorrectData() = runTest {
        val items = listOf(
            InventoryItem(
                id = 1,
                product = "Milk",
                itemUnit = ItemUnit.LITERS,
                amount = 1.0f,
                expirationDate = Date.valueOf(LocalDate.now().minusMonths(1).toString()).toString(),
                price = 3.5f,
                state = ItemState.EXPIRED,
                iconResId = R.drawable.milk_bottle_icon
            ),
            InventoryItem(
                id = 2,
                product = "Cheese",
                itemUnit = ItemUnit.GRAMS,
                amount = 500.0f,
                expirationDate = Date.valueOf(LocalDate.now().minusMonths(2).toString()).toString(),
                price = 4.0f,
                state = ItemState.EXPIRED,
                iconResId = R.drawable.cheese_icon
            )
        )

        items.forEach { inventoryItemDao.upsertInventoryItem(it) }

        val statistics = inventoryItemDao.getMonthlyStatistics()

        assertEquals(2, statistics.size)
        assertEquals(1, statistics[0].count)
        assertEquals("2024-11", statistics[0].month)
        assertEquals("EXPIRED", statistics[0].state)
        assertEquals(1, statistics[1].count)
        assertEquals("2024-12", statistics[1].month)
        assertEquals("EXPIRED", statistics[1].state)
    }

    @Test
    fun getMoneyLostLastMonth_returnsCorrectAmount() = runTest {
        val items = listOf(
            InventoryItem(
                id = 1,
                product = "Milk",
                itemUnit = ItemUnit.LITERS,
                amount = 1.0f,
                expirationDate = Date.valueOf(LocalDate.now().minusDays(10).toString()).toString(),
                price = 3.5f,
                state = ItemState.EXPIRED,
                iconResId = R.drawable.milk_bottle_icon
            ),
            InventoryItem(
                id = 2,
                product = "Banana",
                itemUnit = ItemUnit.PIECES,
                amount = 6.0f,
                expirationDate = Date.valueOf(LocalDate.now().plusDays(10).toString()).toString(),
                price = 2.0f,
                state = ItemState.ACTIVE,
                iconResId = R.drawable.banana_fruit_icon
            )
        )

        items.forEach { inventoryItemDao.upsertInventoryItem(it) }

        val moneyLost = inventoryItemDao.getMoneyLostLastMonth()

        assertEquals(3.5f, moneyLost, 0.01f)
    }

    @Test
    fun getMoneyLostAllTime_returnsCorrectAmount() = runTest {
        val items = listOf(
            InventoryItem(
                id = 1,
                product = "Milk",
                itemUnit = ItemUnit.LITERS,
                amount = 1.0f,
                expirationDate = Date.valueOf(LocalDate.now().minusDays(10).toString()).toString(),
                price = 3.5f,
                state = ItemState.EXPIRED,
                iconResId = R.drawable.milk_bottle_icon
            ),
            InventoryItem(
                id = 2,
                product = "Banana",
                itemUnit = ItemUnit.PIECES,
                amount = 6.0f,
                expirationDate = Date.valueOf(LocalDate.now().minusDays(20).toString()).toString(),
                price = 2.0f,
                state = ItemState.EXPIRED,
                iconResId = R.drawable.banana_fruit_icon
            )
        )

        items.forEach { inventoryItemDao.upsertInventoryItem(it) }

        val totalMoneyLost = inventoryItemDao.getMoneyLostAllTime()

        assertEquals(5.5f, totalMoneyLost, 0.01f)
    }

}
