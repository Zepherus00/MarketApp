package com.example.data.repository

import androidx.lifecycle.LiveData
import com.example.data.model.ProductModel
import com.example.data.network.DataSource
import com.example.data.room.Dependencies.productDao
import com.example.data.room.product.Product
import com.example.data.utilities.toProduct
import com.example.data.utilities.toSaveProductModel
import com.example.domain.model.SaveProductModel
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ProductRepositoryImpl : ProductRepository {
    fun getAllEntities(): LiveData<List<Product>> {
        return productDao.getProducts()
    }

    override fun addProductInFavorites(param: SaveProductModel) {
        val product = param.toProduct()
        CoroutineScope(Dispatchers.IO).launch {
            productDao.insert(product)
        }
    }

    override suspend fun getProductListFromNetwork(): List<SaveProductModel> {
        return CoroutineScope(Dispatchers.IO).async {
            val productList = DataSource().loadData().items
            val saveProductModel = convertProductModelToSaveProduct(productList)
            saveProductModel
        }.await()
    }


    override fun clearTable() {
        CoroutineScope(Dispatchers.IO).launch {
            productDao.clearTable()
        }
    }

    private fun convertProductModelToSaveProduct(products: List<ProductModel>?): List<SaveProductModel> {
        val convertProductList = mutableListOf<SaveProductModel>()

        products?.forEach {
            convertProductList.add(it.toSaveProductModel())
        }
        return convertProductList.toList()
    }
}