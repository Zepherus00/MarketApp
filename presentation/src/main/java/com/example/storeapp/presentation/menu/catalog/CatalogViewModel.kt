package com.example.storeapp.presentation.menu.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.SaveProductModel
import com.example.domain.usecase.AddProductInFavoritesUseCase
import com.example.domain.usecase.GetProductsNetworkUseCase
import com.example.storeapp.presentation.menu.catalog.state.CatalogState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CatalogViewModel(
    private val addProductInFavoritesUseCase: AddProductInFavoritesUseCase,
    val getProductsNetworkUseCase: GetProductsNetworkUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CatalogState>(CatalogState.Loading)
    val state = _state.asStateFlow()

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    private val _productsFromNetwork = MutableLiveData<List<SaveProductModel>>()
    val productsFromNetwork: LiveData<List<SaveProductModel>> = _productsFromNetwork

    init {
        loadData()
    }

    fun onLoadDataClick() {
        _state.value = CatalogState.Loading
        loadData()
    }

    fun markProductAsFavorite(param: SaveProductModel) {
        addProductInFavoritesUseCase.execute(param)
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                getProductsNetworkUseCase.execute {
                    _productsFromNetwork.value = it
                    _state.value = CatalogState.Success
                }
            } catch (e: Exception) {
                _state.value = CatalogState.Error
                _error.send(e.message.toString())
            }
        }
    }
}