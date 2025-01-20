package com.example.wastelessapp.database.entities.inventory_item

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wastelessapp.R
import com.example.wastelessapp.database.WasteLessAppDatabase
import com.example.wastelessapp.database.entities.product.Product
import com.example.wastelessapp.database.entities.product.ProductDao
import com.example.wastelessapp.database.entities.shopping_cart.ShoppingCartDao
import com.example.wastelessapp.database.entities.shopping_cart.ShoppingCartItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.sql.Date
import java.time.LocalDate

//This test class not working due to asynchronized coroutines in viewmodel

@OptIn(ExperimentalCoroutinesApi::class)
//@RunWith(AndroidJUnit4::class)
class InventoryItemViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: InventoryItemViewModel

    private lateinit var database: WasteLessAppDatabase
    private lateinit var inventoryItemDao: InventoryItemDao
    private lateinit var shoppingCartDao: ShoppingCartDao
    private lateinit var productDao: ProductDao

//    private lateinit var inventoryItemDao: FakeInventoryItemDao
//    private lateinit var shoppingCartDao: FakeShoppingCartDao
//    private lateinit var productDao: FakeProductDao

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context,
            WasteLessAppDatabase::class.java
        )
            .build()

        inventoryItemDao = database.inventoryItemDao
        shoppingCartDao = database.shoppingCartDao
        productDao = database.productDao

        viewModel = InventoryItemViewModel(inventoryItemDao, shoppingCartDao, productDao)

        Dispatchers.setMain(testDispatcher)
//        inventoryItemDao = FakeInventoryItemDao()
//        shoppingCartDao = FakeShoppingCartDao()
//        productDao = FakeProductDao()
//        viewModel =
//            InventoryItemViewModel(inventoryItemDao, shoppingCartDao, productDao)

        val basicProducts = listOf(
            Product(name = "Apple", iconResId = R.drawable.apple_fruits_icon),
            Product(name = "Banana", iconResId = R.drawable.banana_fruit_icon),
            Product(name = "Cherry", iconResId = R.drawable.cherry_fruit_icon),
            Product(name = "Orange", iconResId = R.drawable.orange_lemon_icon),
            Product(name = "Lemon", iconResId = R.drawable.orange_lemon_icon),
            Product(name = "Pear", iconResId = R.drawable.pear_papaya_icon),
            Product(name = "Cheese", iconResId = R.drawable.cheese_icon),
            Product(name = "Egg", iconResId = R.drawable.egg_icon),
            Product(name = "Milk", iconResId = R.drawable.milk_bottle_icon),
            Product(name = "Peanut", iconResId = R.drawable.peanut_icon),
            Product(name = "Yogurt", iconResId = R.drawable.milk_bottle_icon)
        )
        runBlocking {
            productDao.insertAll(basicProducts)
        }

        val inventoryItems = listOf(
            InventoryItem(
                id = 1,
                product = "Apple",
                itemUnit = ItemUnit.KILOGRAMS,
                amount = 2.0f,
                expirationDate = Date.valueOf(LocalDate.now().plusDays(5).toString()).toString(),
                price = 5.0f,
                iconResId = R.drawable.apple_fruits_icon
            ),
            InventoryItem(
                id = 2,
                product = "Milk",
                itemUnit = ItemUnit.LITERS,
                amount = 1.5f,
                expirationDate = Date.valueOf(LocalDate.now().plusDays(3).toString()).toString(),
                price = 3.5f,
                iconResId = R.drawable.milk_bottle_icon
            ),
            InventoryItem(
                id = 3,
                product = "Banana",
                itemUnit = ItemUnit.PIECES,
                amount = 6.0f,
                expirationDate = Date.valueOf(LocalDate.now().plusDays(7).toString()).toString(),
                price = 2.0f,
                iconResId = R.drawable.banana_fruit_icon
            ),
            InventoryItem(
                id = 4,
                product = "Cheese",
                itemUnit = ItemUnit.GRAMS,
                amount = 250.0f,
                expirationDate = Date.valueOf(LocalDate.now().plusDays(10).toString()).toString(),
                price = 4.5f,
                iconResId = R.drawable.cheese_icon
            ),
            InventoryItem(
                id = 5,
                product = "Egg",
                itemUnit = ItemUnit.PIECES,
                amount = 12.0f,
                expirationDate = Date.valueOf(LocalDate.now().plusDays(2).toString()).toString(),
                price = 2.5f,
                iconResId = R.drawable.egg_icon
            )
        )
        inventoryItems.forEach { item ->
            runBlocking {
                inventoryItemDao.upsertInventoryItem(item)
            }
        }

        val shoppingCartItems = listOf(
            ShoppingCartItem(
                id = 1,
                product = "Peanut",
                itemUnit = ItemUnit.KILOGRAMS,
                amount = 1.0f,
                iconResId = R.drawable.peanut_icon
            ),
            ShoppingCartItem(
                id = 2,
                product = "Yogurt",
                itemUnit = ItemUnit.LITERS,
                amount = 0.5f,
                iconResId = R.drawable.milk_bottle_icon
            ),
            ShoppingCartItem(
                id = 3,
                product = "Orange",
                itemUnit = ItemUnit.KILOGRAMS,
                amount = 1.5f,
                iconResId = R.drawable.orange_lemon_icon
            ),
            ShoppingCartItem(
                id = 4,
                product = "Cherry",
                itemUnit = ItemUnit.KILOGRAMS,
                amount = 1.0f,
                iconResId = R.drawable.cherry_fruit_icon
            ),
            ShoppingCartItem(
                id = 5,
                product = "Pear",
                itemUnit = ItemUnit.PIECES,
                amount = 3.0f,
                iconResId = R.drawable.pear_papaya_icon
            )
        )
        shoppingCartItems.forEach { item ->
            runBlocking {
                shoppingCartDao.upsertShoppingCartItem(item)
            }
        }

    }

    //@After
    fun tearDown() {
        Dispatchers.resetMain()
        database.close()
    }

    //@Test
    fun `SaveInventoryItem saves item and resets state`() = runTest {

        val product = "Milk"
        val unit = ItemUnit.LITERS
        val amount = 2.0f
        val expirationDate = Date.valueOf(LocalDate.now().plusDays(3).toString())
        val price = 5.99f
        val iconResId = R.drawable.milk_bottle_icon

        val newInventoryItem = InventoryItem(
            id = 6,
            product = product,
            itemUnit = unit,
            amount = amount,
            expirationDate = expirationDate.toString(),
            price = price,
            iconResId = iconResId
        )

        viewModel.onEvent(InventoryItemEvent.SetProduct(product))
        viewModel.onEvent(InventoryItemEvent.SetUnit(unit))
        viewModel.onEvent(InventoryItemEvent.SetAmount(amount))
        viewModel.onEvent(InventoryItemEvent.SetExpirationDate(expirationDate))
        viewModel.onEvent(InventoryItemEvent.SetPrice(price))
        viewModel.onEvent(InventoryItemEvent.SaveInventoryItem)
//        inventoryItemDao.upsertInventoryItem(newInventoryItem)

        advanceUntilIdle()

        val listAfter = inventoryItemDao.getAllInventoryItems().first()
        val state = viewModel.state.value


        assertTrue(listAfter.contains(newInventoryItem))

        assertEquals(false, state.isAddingItem)
        assertEquals("", state.product)
        assertEquals(ItemUnit.PIECES, state.itemUnit)
        assertEquals(0f, state.amount)
        assertEquals(Date.valueOf(LocalDate.now().toString()), state.expirationDate)
        assertEquals(0f, state.price)
        assertEquals(null, state.shoppingCartItemToMove)
    }

}