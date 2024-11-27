package com.example.wastelessapp.database.rooms.product

import java.sql.Date
import java.time.LocalDate

data class ProductState(
    val products: List<Product> = emptyList(),
    val name: String = "",
    val unit: Unit = Unit.PIECES,
    val amount: Float = 0f,
    val expirationDate: Date = Date.valueOf(LocalDate.now().toString()),
    val price: Float = 0f,
    val isAddingProduct: Boolean = false,
    val sortType: SortType = SortType.EXPIRATION_DATE
)