package com.example.storeapp.presentation.di

import com.example.data.repository.ProductRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.ProductRepository
import com.example.domain.repository.UserRepository
import org.koin.dsl.module

val dataModule = module {
    single<UserRepository> {
        UserRepositoryImpl()
    }

    single<ProductRepository> {
        ProductRepositoryImpl()
    }
}