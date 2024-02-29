package com.example.data.network

import com.example.data.model.ProductList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://run.mocky.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val productListApi = retrofit.create(ProductApi::class.java)
}

interface ProductApi {
    @GET("v3/97e721a7-0a66-4cae-b445-83cc0bcf9010")
    suspend fun getProducts(): ProductList
}