package com.example.wastelessapp.database.entities.product

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.ui.graphics.vector.ImageVector


data class ProductState(
    val products: List<Product> = emptyList(),
    val name: String = "",
    val icon: ImageVector = Icons.Filled.AddCircle, //can be null??
    val isAddingProduct: Boolean = false
)
