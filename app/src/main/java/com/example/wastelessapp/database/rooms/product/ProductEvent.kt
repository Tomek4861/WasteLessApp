package com.example.wastelessapp.database.rooms.product

import java.sql.Date

sealed interface ProductEvent {
    object SaveProduct : ProductEvent
    data class SetName(val name: String) : ProductEvent
    data class SetUnit(val unit: Unit) : ProductEvent
    data class SetAmount(val amount: Float) : ProductEvent
    data class SetExpirationDate(val expirationDate: Date) : ProductEvent
    data class SetPrice(val price: Float) : ProductEvent
    object ShowDialog : ProductEvent
    object HideDialog : ProductEvent
    data class SortProducts(val sortType: SortType) : ProductEvent
    data class DeleteProduct(val product: Product) : ProductEvent

}