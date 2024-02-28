package com.example.storeapp.presentation.menu.catalog

import com.example.domain.model.SaveInfo
import com.example.domain.model.SavePrice
import com.example.domain.model.SaveProductModel
import com.example.domain.usecase.AddProductInFavoritesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class ItemViewModelTest {
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val addProductInFavoritesUseCase = mock<AddProductInFavoritesUseCase>()
    private lateinit var viewModel: ItemViewModel

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = ItemViewModel(addProductInFavoritesUseCase)
    }

    @Test
    fun isProductMarkFavorite() {
        val testParam = SaveProductModel(
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

        runBlocking {
            launch(Dispatchers.Main) {
                viewModel.markProductAsFavorites(param = testParam)
            }
        }

        Mockito.verify(addProductInFavoritesUseCase, Mockito.times(1))
            .execute(testParam)
    }
}