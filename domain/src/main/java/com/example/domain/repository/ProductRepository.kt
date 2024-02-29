package com.example.domain.repository

import com.example.domain.model.SaveProductModel

interface ProductRepository {
    fun addProductInFavorites(param: SaveProductModel)

    suspend fun getProductListFromNetwork(): List<SaveProductModel>

    fun clearTable()
}