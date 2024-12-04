package com.example.wastelessapp.database.entities.product

import androidx.compose.ui.graphics.vector.ImageVector

sealed interface ProductEvent {
    object SaveProduct : ProductEvent
    data class SetName(val name: String) : ProductEvent
    data class SetIcon(val icon: ImageVector) : ProductEvent
    object ShowDialog : ProductEvent
    object HideDialog : ProductEvent
    data class DeleteProduct(val product: Product) : ProductEvent
}