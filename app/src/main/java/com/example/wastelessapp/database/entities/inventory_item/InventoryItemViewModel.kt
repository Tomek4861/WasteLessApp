package com.example.wastelessapp.database.entities.inventory_item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val dao: InventoryItemDao
) : ViewModel() {
    private val _sortType = MutableStateFlow(SortType.EXPIRATION_DATE)
    private val _products = _sortType
        .flatMapLatest { sortType ->
            when (sortType) {
                SortType.EXPIRATION_DATE -> dao.getProductsByExpirationDate()
                SortType.NAME -> dao.getProductsByName()
                SortType.AMOUNT -> dao.getProductsByAmount()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(InventoryItemState())
    val state = combine(_state, _sortType, _products) { state, sortType, products ->
        state.copy(
            inventoryItems = products,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), InventoryItemState())

    fun onEvent(event: InventoryItemEvent) {
        when (event) {
            is InventoryItemEvent.DeleteInventoryItem -> {
                viewModelScope.launch {
                    dao.deleteProduct(event.inventoryItem)
                }
            }

            InventoryItemEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingProduct = false
                    )
                }
            }

            InventoryItemEvent.SaveInventoryItem -> {
                val product = state.value.product
                val unit = state.value.itemUnit
                val amount = state.value.amount
                val expirationDate = state.value.expirationDate
                val price = state.value.price

                //TODO checks for unit, date, amount on frontend side
//                if (name.isBlank() || amount.isNaN() )
//                    return

                val inventoryItem = InventoryItem(
                    product = product,
                    itemUnit = unit,
                    amount = amount,
                    expirationDate = expirationDate,
                    price = price
                )
                viewModelScope.launch {
                    dao.upsertProduct(inventoryItem)
                }
                _state.update { it.copy(
                    isAddingProduct = false,
                    product = 0,
                    itemUnit = ItemUnit.PIECES,
                    amount = 0f,
                    expirationDate = Date.valueOf(LocalDate.now().toString()),
                    price = 0f
                ) }
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
                        isAddingProduct = true
                    )
                }
            }

            is InventoryItemEvent.SortProducts -> {
                _sortType.value = event.sortType
            }
        }
    }
}