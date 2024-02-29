package com.example.data.network

import com.example.data.model.ProductList
import java.net.ConnectException

class DataSource {
    suspend fun loadData(): ProductList {
        val data: ProductList?
        try {
            data = RetrofitInstance.productListApi.getProducts()
        } catch (t: Throwable) {
            throw ConnectException(t.message)
        }
        return data
    }
}