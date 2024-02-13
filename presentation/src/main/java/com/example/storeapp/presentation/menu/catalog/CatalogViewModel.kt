package com.example.storeapp.presentation.menu.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.SaveProductModel
import com.example.domain.usecase.AddProductInFavoritesUseCase
import com.example.domain.usecase.GetProductsNetworkUseCase

class CatalogViewModel(
    private val addProductInFavoritesUseCase: AddProductInFavoritesUseCase,
    getProductsNetworkUseCase: GetProductsNetworkUseCase
) : ViewModel() {

    private val _productsFromNetwork = MutableLiveData<List<SaveProductModel>>()
    val productsFromNetwork: LiveData<List<SaveProductModel>> = _productsFromNetwork

    init {
        getProductsNetworkUseCase.execute {
            _productsFromNetwork.value = it
        }
    }

    fun markProductAsFavorite(param: SaveProductModel) {
        addProductInFavoritesUseCase.execute(param)
    }
}