package com.example.wastelessapp.database.entities.shopping_cart

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wastelessapp.R
import com.example.wastelessapp.database.WasteLessAppDatabase
import com.example.wastelessapp.database.entities.inventory_item.ItemUnit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShoppingCartDaoTest {

    private lateinit var database: WasteLessAppDatabase
    private lateinit var shoppingCartDao: ShoppingCartDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            WasteLessAppDatabase::class.java
        ).build()

        shoppingCartDao = database.shoppingCartDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun upsertShoppingCartItem_insertsAndUpdatesItem() = runTest {
        val item = ShoppingCartItem(
            id = 1,
            product = "Milk",
            amount = 1.5f,
            itemUnit = ItemUnit.LITERS,
            iconResId = R.drawable.milk_bottle_icon
        )

        shoppingCartDao.upsertShoppingCartItem(item)

        val items = shoppingCartDao.getShoppingCartItemsByName().first()
        assertEquals(1, items.size)
        assertEquals("Milk", items[0].product)

        val updatedItem = item.copy(amount = 2.0f)
        shoppingCartDao.upsertShoppingCartItem(updatedItem)

        val updatedItems = shoppingCartDao.getShoppingCartItemsByName().first()
        assertEquals(1, updatedItems.size)
        assertEquals(2.0f, updatedItems[0].amount, 0.0f)
    }

    @Test
    fun deleteShoppingCartItem_removesItem() = runTest {
        val item = ShoppingCartItem(
            id = 1,
            product = "Milk",
            amount = 1.5f,
            itemUnit = ItemUnit.LITERS,
            iconResId = R.drawable.milk_bottle_icon
        )

        shoppingCartDao.upsertShoppingCartItem(item)

        val items = shoppingCartDao.getShoppingCartItemsByName().first()
        assertEquals(1, items.size)

        shoppingCartDao.deleteShoppingCartItem(item)

        val itemsAfterDelete = shoppingCartDao.getShoppingCartItemsByName().first()
        assertTrue(itemsAfterDelete.isEmpty())
    }

    @Test
    fun getShoppingCartItemsByName_returnsSortedItems() = runTest {
        val item1 = ShoppingCartItem(
            id = 1,
            product = "Banana",
            amount = 2.0f,
            itemUnit = ItemUnit.PIECES,
            iconResId = R.drawable.banana_fruit_icon
        )
        val item2 = ShoppingCartItem(
            id = 2,
            product = "Apple",
            amount = 1.0f,
            itemUnit = ItemUnit.KILOGRAMS,
            iconResId = R.drawable.apple_fruits_icon
        )

        shoppingCartDao.upsertShoppingCartItem(item1)
        shoppingCartDao.upsertShoppingCartItem(item2)

        val items = shoppingCartDao.getShoppingCartItemsByName().first()
        assertEquals(2, items.size)
        assertEquals("Apple", items[0].product)
        assertEquals("Banana", items[1].product)
    }

    @Test
    fun getShoppingCartItemsByIdOrder_returnsSortedByIdDescending() = runTest {
        val item1 = ShoppingCartItem(
            id = 1,
            product = "Milk",
            amount = 1.5f,
            itemUnit = ItemUnit.LITERS,
            iconResId = R.drawable.milk_bottle_icon
        )
        val item2 = ShoppingCartItem(
            id = 2,
            product = "Cheese",
            amount = 0.5f,
            itemUnit = ItemUnit.KILOGRAMS,
            iconResId = R.drawable.cheese_icon
        )

        shoppingCartDao.upsertShoppingCartItem(item1)
        shoppingCartDao.upsertShoppingCartItem(item2)

        val items = shoppingCartDao.getShoppingCartItemsByIdOrder().first()
        assertEquals(2, items.size)
        assertEquals(2, items[0].id)
        assertEquals(1, items[1].id)
    }

    @Test
    fun deleteAllShoppingCartItems_clearsTheTable() = runTest {
        val item1 = ShoppingCartItem(
            id = 1,
            product = "Milk",
            amount = 1.5f,
            itemUnit = ItemUnit.LITERS,
            iconResId = R.drawable.milk_bottle_icon
        )
        val item2 = ShoppingCartItem(
            id = 2,
            product = "Cheese",
            amount = 0.5f,
            itemUnit = ItemUnit.KILOGRAMS,
            iconResId = R.drawable.cheese_icon
        )

        shoppingCartDao.upsertShoppingCartItem(item1)
        shoppingCartDao.upsertShoppingCartItem(item2)

        val items = shoppingCartDao.getShoppingCartItemsByName().first()
        assertEquals(2, items.size)

        shoppingCartDao.deleteAllShoppingCartItems()

        val itemsAfterDelete = shoppingCartDao.getShoppingCartItemsByName().first()
        assertTrue(itemsAfterDelete.isEmpty())
    }
}
