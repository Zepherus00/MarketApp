package com.example.domain.usecase

import com.example.domain.model.SaveProductModel
import com.example.domain.repository.ProductRepository

class GetProductsNetworkUseCase(private val productRepository: ProductRepository) {

    fun execute(function: (List<SaveProductModel>) -> Unit) {
        productRepository.getProductListFromNetwork { productList ->
            function(productList)
        }
    }
}