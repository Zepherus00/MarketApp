package com.example.domain.usecase

import com.example.domain.model.SaveProductModel
import com.example.domain.repository.ProductRepository

class GetProductsNetworkUseCase(private val productRepository: ProductRepository) {

    suspend fun execute(): List<SaveProductModel> {
        return productRepository.getProductListFromNetwork()
    }
}