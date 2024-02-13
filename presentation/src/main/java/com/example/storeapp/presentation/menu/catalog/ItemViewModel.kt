package com.example.storeapp.presentation.menu.catalog

import androidx.lifecycle.ViewModel
import com.example.domain.model.SaveProductModel
import com.example.domain.usecase.AddProductInFavoritesUseCase

class ItemViewModel(
    private val addProductInFavoritesUseCase: AddProductInFavoritesUseCase
) : ViewModel() {

    fun markProductAsFavorites(param: SaveProductModel) {
        addProductInFavoritesUseCase.execute(param)
    }
}