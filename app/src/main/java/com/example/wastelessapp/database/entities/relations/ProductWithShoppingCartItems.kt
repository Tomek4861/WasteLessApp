package com.example.wastelessapp.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.wastelessapp.database.entities.product.Product
import com.example.wastelessapp.database.entities.shopping_cart.ShoppingCartItem

data class ProductWithShoppingCartItems (
    @Embedded
    val product: Product,
    @Relation(
        parentColumn = "name",
        entityColumn = "product"
    )
    val shoppingCartItems: List<ShoppingCartItem>
)