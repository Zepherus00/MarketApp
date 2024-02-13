package com.example.storeapp.presentation.menu.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.data.repository.ProductRepositoryImpl
import com.example.data.room.product.Product
import com.example.domain.usecase.ExitFromAppUseCase

class ProfileViewModel(
    productRepositoryImpl: ProductRepositoryImpl,
    private val exitFromAppUseCase: ExitFromAppUseCase
) : ViewModel() {
    val productList: LiveData<List<Product>> = productRepositoryImpl.getAllEntities()

    fun exitFromApp() {
        exitFromAppUseCase.execute()
    }
}