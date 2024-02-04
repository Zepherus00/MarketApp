package com.example.storeapp.utilities

import com.example.storeapp.MainActivity
import com.example.storeapp.data.UserModel
import com.example.storeapp.entity.product.ProductDao
import com.example.storeapp.entity.user.UserDao

lateinit var APP_ACTIVITY: MainActivity
lateinit var userDao: UserDao
lateinit var productDao: ProductDao
lateinit var userModel: UserModel