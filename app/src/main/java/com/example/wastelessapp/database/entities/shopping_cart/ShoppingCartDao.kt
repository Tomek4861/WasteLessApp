package com.example.wastelessapp.database.entities.shopping_cart

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingCartDao {

    @Upsert
    suspend fun upsertShoppingCartItem(shoppingCartItem: ShoppingCartItem)

    @Delete
    suspend fun deleteShoppingCartItem(shoppingCartItem: ShoppingCartItem)

    @Transaction
    @Query("SELECT * FROM shoppingcartitem ORDER BY product ASC")
    fun getShoppingCartItemsByName(): Flow<List<ShoppingCartItem>>

    @Query("SELECT * FROM shoppingcartitem ORDER BY id DESC")
    fun getShoppingCartItemsByIdOrder(): Flow<List<ShoppingCartItem>>

    @Query("DELETE FROM shoppingcartitem")
    suspend fun deleteAllShoppingCartItems()

}