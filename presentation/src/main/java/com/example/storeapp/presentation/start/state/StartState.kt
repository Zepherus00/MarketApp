package com.example.storeapp.presentation.start.state

sealed class StartState {
    data object Loading : StartState()
    data object Success : StartState()
}