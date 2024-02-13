package com.example.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.data.model.ProductList
import com.example.data.model.ProductModel
import com.example.data.network.ProductService
import com.example.data.room.Dependencies.productDao
import com.example.data.room.product.Product
import com.example.data.utilities.toProduct
import com.example.data.utilities.toSaveProductModel
import com.example.domain.model.SaveProductModel
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call

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

    override fun getProductListFromNetwork(function: (List<SaveProductModel>) -> Unit) {
        val call = ProductService.productService.getProducts()

        call.enqueue(object : retrofit2.Callback<ProductList> {
            override fun onResponse(
                call: Call<ProductList>,
                response: retrofit2.Response<ProductList>
            ) {
                if (response.isSuccessful) {
                    val tempProductList = response.body()?.items
                    val products = convertProductModelToSaveProduct(tempProductList)
                    function(products)
                } else {
                    Log.d("error", "Mistake")
                }
            }

            override fun onFailure(call: Call<ProductList>, t: Throwable) {
                Log.d("error", "Mistake")
            }
        })
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