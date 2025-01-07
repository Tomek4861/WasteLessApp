package com.example.wastelessapp.database.entities.shopping_cart

import com.example.wastelessapp.database.entities.inventory_item.ItemUnit

data class ShoppingCartState (
    val shoppingCartItems: List<ShoppingCartItem> = emptyList(),
    val product: String = "", //TODO check is can be null
    val itemUnit: ItemUnit = ItemUnit.PIECES,
    val amount: Float = 0f,
    val isAddingItem: Boolean = false,
    val sortType: ShoppingCartSortType = ShoppingCartSortType.NAME
)