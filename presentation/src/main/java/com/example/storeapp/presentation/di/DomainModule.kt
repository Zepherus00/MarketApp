package com.example.storeapp.presentation.di

import com.example.domain.usecase.AddNewUserUseCase
import com.example.domain.usecase.AddProductInFavoritesUseCase
import com.example.domain.usecase.ExitFromAppUseCase
import com.example.domain.usecase.GetProductsNetworkUseCase
import org.koin.dsl.module

val domainModule = module {
    factory<AddNewUserUseCase> {
        AddNewUserUseCase(userRepository = get())
    }

    factory<ExitFromAppUseCase> {
        ExitFromAppUseCase(
            productRepository = get(),
            userRepository = get()
        )
    }

    factory<AddProductInFavoritesUseCase> {
        AddProductInFavoritesUseCase(productRepository = get())
    }

    factory<AddProductInFavoritesUseCase> {
        AddProductInFavoritesUseCase(productRepository = get())
    }

    factory<GetProductsNetworkUseCase> {
        GetProductsNetworkUseCase(productRepository = get())
    }
}