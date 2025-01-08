package com.example.wastelessapp.database.entities.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wastelessapp.R
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
    private val _products = dao.getProductsByName()
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
                val iconResId = state.value.iconResId

                val product = Product(
                    name = name,
                    iconResId = iconResId
                )
                viewModelScope.launch {
                    dao.upsertProduct(product)
                }
                _state.update { it.copy(
                    isAddingProduct = false,
                    name = "",
                    iconResId = R.drawable.dish_spoon_knife_icon
                ) }
                println("Product saved $product")
            }

            is ProductEvent.SetIconResId -> {
                _state.update {
                    it.copy(
                        iconResId = event.iconResId
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