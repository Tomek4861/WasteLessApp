package com.example.wastelessapp.database.entities.shopping_cart

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

interface ShoppingCartDao {

    @Upsert
    suspend fun upsertShoppingCartItem(shoppingCartItem: ShoppingCartItem)

    @Delete
    suspend fun deleteShoppingCartItem(shoppingCartItem: ShoppingCartItem)

    @Transaction
    @Query("SELECT * FROM shoppingcartitem ORDER BY product ASC")
    fun getShoppingCartItems(): Flow<List<ShoppingCartItem>>


}