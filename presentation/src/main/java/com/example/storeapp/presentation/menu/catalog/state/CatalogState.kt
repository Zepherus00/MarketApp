package com.example.storeapp.presentation.menu.catalog.state

sealed class CatalogState {
    data object Loading : CatalogState()
    data object Success : CatalogState()
    data object Error : CatalogState()
}