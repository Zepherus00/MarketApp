package com.example.data.room

import android.content.Context
import androidx.room.Room
import com.example.data.room.product.ProductDao
import com.example.data.room.product.ProductDatabase
import com.example.data.room.user.UserDao
import com.example.data.room.user.UserDatabase

object Dependencies {
    private lateinit var applicationContext: Context
    internal lateinit var userDao: UserDao
    internal lateinit var productDao: ProductDao

    fun init(context: Context) {
        applicationContext = context
        userDao = dbUsers.userDao()
        productDao = dbProducts.productDao()
    }

    private val dbUsers: UserDatabase by lazy {
        Room.databaseBuilder(applicationContext, UserDatabase::class.java, "dbUsers")
            .build()
    }

    private val dbProducts: ProductDatabase by lazy {
        Room.databaseBuilder(applicationContext, ProductDatabase::class.java, "dbProducts")
            .build()
    }
}