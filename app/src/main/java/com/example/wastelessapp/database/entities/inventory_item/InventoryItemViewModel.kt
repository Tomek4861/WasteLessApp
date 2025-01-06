package com.example.wastelessapp.database.entities.inventory_item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wastelessapp.database.entities.shopping_cart.ShoppingCartDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.sql.Date
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class InventoryItemViewModel(
    private val dao: InventoryItemDao,
    private val shoppingCartDao: ShoppingCartDao
) : ViewModel() {
    private val _sortType = MutableStateFlow(SortType.EXPIRATION_DATE)
    private val _inventoryItems = _sortType
        .flatMapLatest { sortType ->
            when (sortType) {
                SortType.EXPIRATION_DATE -> dao.getActiveInventoryItemsByExpirationDate()
                SortType.NAME -> dao.getActiveInventoryItemsByName()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(InventoryItemState())
    val state = combine(_state, _sortType, _inventoryItems) { state, sortType, inventoryItems ->
        state.copy(
            inventoryItems = inventoryItems,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), InventoryItemState())

    fun onEvent(event: InventoryItemEvent) {
        when (event) {
            is InventoryItemEvent.DeleteInventoryItem -> {
                viewModelScope.launch {
                    dao.deleteInventoryItem(event.inventoryItem)
                }
            }

            InventoryItemEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingItem = false,
                        shoppingCartItemToMove = null
                    )
                }
            }

            InventoryItemEvent.SaveInventoryItem -> {
                val product = state.value.product
                val unit = state.value.itemUnit
                val amount = state.value.amount
                val expirationDate = state.value.expirationDate
                val price = state.value.price

                val inventoryItem = InventoryItem(
                    product = product,
                    itemUnit = unit,
                    amount = amount,
                    expirationDate = expirationDate,
                    price = price
                )
                viewModelScope.launch {
                    dao.upsertInventoryItem(inventoryItem)

                    if (state.value.shoppingCartItemToMove != null){
                        shoppingCartDao.deleteShoppingCartItem(state.value.shoppingCartItemToMove!!)
                    }

                }
                _state.update {
                    it.copy(
                        isAddingItem = false,
                        product = "",
                        itemUnit = ItemUnit.PIECES,
                        amount = 0f,
                        expirationDate = Date.valueOf(LocalDate.now().toString()),
                        price = 0f,
                        shoppingCartItemToMove = null
                    )
                }
            }

            is InventoryItemEvent.SetAmount -> {
                _state.update {
                    it.copy(
                        amount = event.amount
                    )
                }
            }

            is InventoryItemEvent.SetExpirationDate -> {
                _state.update {
                    it.copy(
                        expirationDate = event.expirationDate
                    )
                }
            }

            is InventoryItemEvent.SetProduct -> {
                _state.update {
                    it.copy(
                        product = event.product
                    )
                }
            }

            is InventoryItemEvent.SetPrice -> {
                _state.update {
                    it.copy(
                        price = event.price
                    )
                }
            }

            is InventoryItemEvent.SetUnit -> {
                _state.update {
                    it.copy(
                        itemUnit = event.itemUnit
                    )
                }
            }

            InventoryItemEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingItem = true
                    )
                }
            }

            is InventoryItemEvent.SortProducts -> {
                _sortType.value = event.sortType
            }

            is InventoryItemEvent.UpdateItemState -> {
                val state: ItemState =
                    if (Date.valueOf(LocalDate.now().toString()) <= event.inventoryItem.expirationDate) {
                        ItemState.SAVED
                    } else {
                        ItemState.EXPIRED
                    }
                println("Updated product state: $state")

                viewModelScope.launch {
                    dao.updateItemState(event.inventoryItem.id, state)
                }
            }

            is InventoryItemEvent.MoveShoppingCartItem -> {
                val shoppingCartItem = event.shoppingCartItem
                _state.update {
                    it.copy(
                        isAddingItem = true,
                        shoppingCartItemToMove = shoppingCartItem,
                        product = shoppingCartItem.product,
                        amount = shoppingCartItem.amount,
                        itemUnit = shoppingCartItem.itemUnit
                    )
                }
            }
        }
    }
}