package com.example.domain.repository

import com.example.domain.model.SaveProductModel

interface ProductRepository {
    fun addProductInFavorites(param: SaveProductModel)

    fun getProductListFromNetwork(function: (List<SaveProductModel>) -> Unit)

    fun clearTable()
}