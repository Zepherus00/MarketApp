package com.example.storeapp.presentation.di

import com.example.storeapp.presentation.menu.catalog.CatalogViewModel
import com.example.storeapp.presentation.menu.catalog.ItemViewModel
import com.example.storeapp.presentation.menu.profile.ProductsViewModel
import com.example.storeapp.presentation.menu.profile.ProfileViewModel
import com.example.storeapp.presentation.start.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel<RegistrationViewModel> {
        RegistrationViewModel(addNewUserUseCase = get())
    }

    viewModel<ProfileViewModel> {
        ProfileViewModel(
            productRepositoryImpl = get(),
            exitFromAppUseCase = get()
        )
    }

    viewModel<ProductsViewModel> {
        ProductsViewModel(productRepositoryImpl = get())
    }

    viewModel<ItemViewModel> {
        ItemViewModel(addProductInFavoritesUseCase = get())
    }

    viewModel<CatalogViewModel> {
        CatalogViewModel(
            addProductInFavoritesUseCase = get(),
            getProductsNetworkUseCase = get()
        )
    }
}