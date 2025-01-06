package com.example.wastelessapp.database.entities.product

sealed interface ProductEvent {
    data object SaveProduct : ProductEvent
    data class SetName(val name: String) : ProductEvent
    data class SetIconResId(val iconResId: Int) : ProductEvent
    data object ShowDialog : ProductEvent
    data object HideDialog : ProductEvent
    data class DeleteProduct(val product: Product) : ProductEvent
}
