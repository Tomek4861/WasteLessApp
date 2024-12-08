package com.example.wastelessapp.database.entities.product

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModel(
    private val dao: ProductDao
) : ViewModel() {
    private val _products = dao.getProducts()
    private val _state = MutableStateFlow(ProductState())

    val state = combine(_state, _products) { state, products ->
        state.copy(
            products = products
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductState())

    fun onEvent(event: ProductEvent){
        when (event){
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
                val icon = state.value.icon

                //if (name.isBlank())
                //    return

                val product = Product(
                    name = name,
                    icon = icon
                )
                viewModelScope.launch {
                    dao.upsertProduct(product)
                }
                _state.update { it.copy(
                    isAddingProduct = false,
                    name = "",
                    icon = Icons.Filled.AddCircle
                ) }
            }

            is ProductEvent.SetIcon -> {
                _state.update {
                    it.copy(
                        icon = event.icon
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

            ProductEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingProduct = true
                    )
                }
            }

        }
    }
}