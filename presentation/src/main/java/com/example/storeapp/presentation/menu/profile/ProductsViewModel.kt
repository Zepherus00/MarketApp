package com.example.storeapp.presentation.menu.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.data.repository.ProductRepositoryImpl
import com.example.data.room.product.Product

class ProductsViewModel(
    productRepositoryImpl: ProductRepositoryImpl
) : ViewModel() {
    val productList: LiveData<List<Product>> = productRepositoryImpl.getAllEntities()
}