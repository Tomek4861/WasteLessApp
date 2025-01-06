package com.example.wastelessapp.database.entities.product

import com.example.wastelessapp.R


data class ProductState(
    val products: List<Product> = emptyList(),
    val name: String = "",
    val iconResId: Int = R.drawable.dish_spoon_knife_icon,
    val isAddingProduct: Boolean = false
)
