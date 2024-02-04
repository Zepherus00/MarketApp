package com.example.storeapp

import android.app.Application
import androidx.room.Room
import com.example.storeapp.entity.product.ProductDatabase
import com.example.storeapp.entity.user.UserDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    lateinit var dbUsers: UserDatabase
    lateinit var dbProducts: ProductDatabase

    override fun onCreate() {
        super.onCreate()

        dbUsers = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "dbUsers"
        ).build()

        dbProducts = Room.databaseBuilder(
            applicationContext,
            ProductDatabase::class.java,
            "dbProducts"
        ).build()
    }
}