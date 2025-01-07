package com.example.wastelessapp.database.entities.shopping_cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wastelessapp.database.entities.inventory_item.ItemUnit
import com.example.wastelessapp.database.entities.product.ProductDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ShoppingCartViewModel(
    private val shoppingCartDao: ShoppingCartDao,
    private val productDao: ProductDao
) : ViewModel() {
    private val _sortType = MutableStateFlow(ShoppingCartSortType.NAME)
    private val _shoppingCartItems = _sortType
        .flatMapLatest { sortType ->
            when (sortType) {
                ShoppingCartSortType.NAME -> shoppingCartDao.getShoppingCartItemsByName()
                ShoppingCartSortType.DATE_ADDED -> shoppingCartDao.getShoppingCartItemsByIdOrder()
            }
        }

    private val _state = MutableStateFlow(ShoppingCartState())

    val state = combine(_state, _sortType, _shoppingCartItems) { state, sortType, shoppingCartItems ->
        state.copy(
            shoppingCartItems = shoppingCartItems,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ShoppingCartState())

    fun onEvent(event: ShoppingCartEvent) {
        when (event) {
            is ShoppingCartEvent.DeleteShoppingCartItem -> {
                viewModelScope.launch {
                    shoppingCartDao.deleteShoppingCartItem(event.shoppingCartItem)
                }
            }

            ShoppingCartEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingItem = false
                    )
                }
            }

            ShoppingCartEvent.SaveShoppingCartItem -> {
                val product = state.value.product
                val unit = state.value.itemUnit
                val amount = state.value.amount

                val shoppingCartItem = ShoppingCartItem(
                    product = product,
                    itemUnit = unit,
                    amount = amount,
                    iconResId = productDao.getIconResIdByProductName(product)!!
                )
                viewModelScope.launch {
                    shoppingCartDao.upsertShoppingCartItem(shoppingCartItem)
                }
                _state.update {
                    it.copy(
                        isAddingItem = false,
                        product = "",
                        itemUnit = ItemUnit.PIECES,
                        amount = 0f,
                    )
                }
            }

            is ShoppingCartEvent.SetAmount -> {
                _state.update {
                    it.copy(
                        amount = event.amount
                    )
                }
            }

            is ShoppingCartEvent.SetProduct -> {
                _state.update {
                    it.copy(
                        product = event.product
                    )
                }
            }

            is ShoppingCartEvent.SetUnit -> {
                _state.update {
                    it.copy(
                        itemUnit = event.itemUnit
                    )
                }
            }

            ShoppingCartEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingItem = true
                    )
                }
            }

            is ShoppingCartEvent.SortProducts -> {
                _sortType.value = event.sortType
            }

            ShoppingCartEvent.DeleteAllShoppingCartItems -> {
                viewModelScope.launch {
                    shoppingCartDao.deleteAllShoppingCartItems()
                }
            }
        }
    }
}