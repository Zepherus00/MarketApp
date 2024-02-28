package com.example.domain.usecase

import com.example.domain.model.SaveInfo
import com.example.domain.model.SavePrice
import com.example.domain.model.SaveProductModel
import com.example.domain.repository.ProductRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class AddProductInFavoritesUseCaseTest {

    private val productRepository = mock<ProductRepository>()

    @Test
    fun isAddProductInFavorites() {
        val saveProductModel = SaveProductModel(
            id = "1",
            title = "title",
            subtitle = "subtitle",
            price = SavePrice(
                price = "10",
                discount = 1,
                priceWithDiscount = "9",
                unit = "$"
            ),
            feedback = null,
            tags = emptyList(),
            available = 100,
            description = "Description",
            info = listOf(
                SaveInfo(
                    title = "save title",
                    value = "save value"
                )
            ),
            ingredients = "ingredients"
        )
        val addProductInFavoritesUseCase =
            AddProductInFavoritesUseCase(productRepository = productRepository)

        addProductInFavoritesUseCase.execute(param = saveProductModel)

        Mockito.verify(productRepository).addProductInFavorites(param = saveProductModel)
    }
}