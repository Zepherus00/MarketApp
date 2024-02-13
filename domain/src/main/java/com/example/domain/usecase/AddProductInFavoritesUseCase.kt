package com.example.domain.usecase

import com.example.domain.model.SaveProductModel
import com.example.domain.repository.ProductRepository

class AddProductInFavoritesUseCase(private val productRepository: ProductRepository) {

    fun execute(param: SaveProductModel) {
        productRepository.addProductInFavorites(param)
    }
}