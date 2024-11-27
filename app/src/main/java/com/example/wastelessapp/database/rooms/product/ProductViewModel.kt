package com.example.wastelessapp.database.rooms.product

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
class ProductViewModel(
    private val dao: ProductDao
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

    private val _state = MutableStateFlow(ProductState())
    val state = combine(_state, _sortType, _products) { state, sortType, products ->
        state.copy(
            products = products,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductState())

    fun onEvent(event: ProductEvent) {
        when (event) {
            is ProductEvent.DeleteProduct -> {
                viewModelScope.launch {
                    dao.deleteProduct(event.product)
                }
            }

            ProductEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingProduct = false
                    )
                }
            }

            ProductEvent.SaveProduct -> {
                val name = state.value.name
                val unit = state.value.unit
                val amount = state.value.amount
                val expirationDate = state.value.expirationDate
                val price = state.value.price

                //TODO checks for unit, date, amount
                if (name.isBlank() || amount.isNaN() )
                    return

                val product = Product(
                    name = name,
                    unit = unit,
                    amount = amount,
                    expirationDate = expirationDate,
                    price = price
                )
                viewModelScope.launch {
                    dao.upsertProduct(product)
                }
                _state.update { it.copy(
                    isAddingProduct = false,
                    name = "",
                    unit = Unit.PIECES,
                    amount = 0f,
                    expirationDate = Date.valueOf(LocalDate.now().toString()),
                    price = 0f
                ) }
            }

            is ProductEvent.SetAmount -> {
                _state.update {
                    it.copy(
                        amount = event.amount
                    )
                }
            }

            is ProductEvent.SetExpirationDate -> {
                _state.update {
                    it.copy(
                        expirationDate = event.expirationDate
                    )
                }
            }

            is ProductEvent.SetName -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            }

            is ProductEvent.SetPrice -> {
                _state.update {
                    it.copy(
                        price = event.price
                    )
                }
            }

            is ProductEvent.SetUnit -> {
                _state.update {
                    it.copy(
                        unit = event.unit
                    )
                }
            }

            ProductEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingProduct = true
                    )
                }
            }

            is ProductEvent.SortProducts -> {
                _sortType.value = event.sortType
            }
        }
    }
}