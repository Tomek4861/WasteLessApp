package com.example.wastelessapp.database.entities.shopping_cart

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeShoppingCartDao : ShoppingCartDao {
    private val shoppingCartItems = mutableListOf<ShoppingCartItem>()

    override suspend fun upsertShoppingCartItem(shoppingCartItem: ShoppingCartItem) {
        shoppingCartItems.removeIf { it.id == shoppingCartItem.id }
        shoppingCartItems.add(shoppingCartItem)
    }

    override suspend fun deleteShoppingCartItem(shoppingCartItem: ShoppingCartItem) {
        shoppingCartItems.remove(shoppingCartItem)
    }

    override fun getShoppingCartItemsByName(): Flow<List<ShoppingCartItem>> {
        return flowOf(shoppingCartItems.sortedBy { it.product })
    }

    override fun getShoppingCartItemsByIdOrder(): Flow<List<ShoppingCartItem>> {
        return flowOf(shoppingCartItems.sortedByDescending { it.id })
    }

    override suspend fun deleteAllShoppingCartItems() {
        shoppingCartItems.clear()
    }
}
