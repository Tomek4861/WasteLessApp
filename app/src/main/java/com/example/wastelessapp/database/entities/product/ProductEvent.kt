package com.example.wastelessapp.database.entities.product

import androidx.compose.ui.graphics.vector.ImageVector

sealed interface ProductEvent {
    data object SaveProduct : ProductEvent
    data class SetName(val name: String) : ProductEvent
    data class SetIcon(val icon: ImageVector) : ProductEvent
    data object ShowDialog : ProductEvent
    data object HideDialog : ProductEvent
    data class DeleteProduct(val product: Product) : ProductEvent
}