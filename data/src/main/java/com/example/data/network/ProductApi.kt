package com.example.data.network

import com.example.data.model.ProductList
import retrofit2.Call
import retrofit2.http.GET

interface ProductApi {
    @GET("v3/97e721a7-0a66-4cae-b445-83cc0bcf9010")
    fun getProducts(): Call<ProductList>
}