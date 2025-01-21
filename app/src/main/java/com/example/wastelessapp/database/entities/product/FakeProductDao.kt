package com.example.wastelessapp.database.entities.product

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeProductDao : ProductDao {
    private val products = mutableListOf<Product>()

    override suspend fun upsertProduct(product: Product) {
        products.removeIf { it.name == product.name }
        products.add(product)
    }

    override suspend fun deleteProduct(product: Product) {
        products.remove(product)
    }

    override fun getProductsByName(): Flow<List<Product>> {
        return flowOf(products.sortedBy { it.name })
    }

    override suspend fun getIconResIdByProductName(productName: String): Int? {
        return products.find { it.name == productName }?.iconResId
    }

    override suspend fun insertAll(products: List<Product>) {
        this.products.addAll(products.filter { newProduct ->
            this.products.none { it.name == newProduct.name }
        })
    }
}
