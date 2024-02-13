package com.example.storeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.data.room.Dependencies
import com.example.storeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        APP_ACTIVITY = this
        Dependencies.init(applicationContext)
    }
}