package com.example.storeapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.storeapp.entity.product.Product
import com.example.storeapp.entity.user.User
import com.example.storeapp.utilities.productDao
import com.example.storeapp.utilities.userDao
import com.example.storeapp.utilities.userModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository {

    fun getData(function: (List<ProductModel>) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val productService = retrofit.create(ProductApi::class.java)

        val call = productService.getProducts()

        call.enqueue(object : retrofit2.Callback<ProductList> {
            override fun onResponse(
                call: Call<ProductList>,
                response: retrofit2.Response<ProductList>
            ) {
                if (response.isSuccessful) {
                    val tempProductList = response.body()
                    val products = tempProductList?.items

                    if (products != null) {
                        function(products)

                    }
                } else {
                    Log.d("error", "Mistake")
                }
            }

            override fun onFailure(call: Call<ProductList>, t: Throwable) {
                Log.d("error", "Mistake")
            }
        })
    }

    fun insertUser(firstName: String, lastName: String, phoneNumber: String) {
        userModel = UserModel(firstName, lastName, phoneNumber)
        CoroutineScope(Dispatchers.IO).launch {
            val user = User(
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber,
                isLoggedIn = true
            )
            userDao.insert(user)
        }
    }

    fun insertProduct(productModel: ProductModel) {
        val product = Product(
            productModel.id,
            productModel.title,
            productModel.subtitle,
            productModel.price.price,
            productModel.price.discount,
            productModel.price.priceWithDiscount,
            productModel.price.unit,
            productModel.feedback?.count,
            productModel.feedback?.rating
        )
        CoroutineScope(Dispatchers.IO).launch {
            productDao.insert(product)
        }
    }

    fun getAllEntities(): LiveData<List<Product>> {
        return productDao.getProducts()
    }
}