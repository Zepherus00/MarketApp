package com.example.storeapp.presentation.menu.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.storeapp.data.Repository
import com.example.storeapp.entity.product.Product

class ProductsViewModel : ViewModel() {
    private val repository = Repository()
    val productList: LiveData<List<Product>> = repository.getAllEntities()
}