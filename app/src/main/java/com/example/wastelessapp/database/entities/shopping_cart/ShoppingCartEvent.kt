package com.example.wastelessapp.database.entities.shopping_cart

import com.example.wastelessapp.database.entities.inventory_item.ItemUnit

sealed interface ShoppingCartEvent {
    data object SaveShoppingCartItem : ShoppingCartEvent
    data class SetProduct(val product: String) : ShoppingCartEvent
    data class SetUnit(val itemUnit: ItemUnit) : ShoppingCartEvent
    data class SetAmount(val amount: Float) : ShoppingCartEvent
    data object ShowDialog : ShoppingCartEvent
    data object HideDialog : ShoppingCartEvent
    data class DeleteShoppingCartItem(val shoppingCartItem: ShoppingCartItem) : ShoppingCartEvent
}