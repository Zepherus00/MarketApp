package com.example.storeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.storeapp.utilities.APP_ACTIVITY
import com.example.storeapp.utilities.productDao
import com.example.storeapp.utilities.userDao
import com.example.storeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        APP_ACTIVITY = this
        setLocalBaseData()
    }

    private fun setLocalBaseData() {
        userDao = (application as App).dbUsers.userDao()
        productDao = (application as App).dbProducts.productDao()
    }
}