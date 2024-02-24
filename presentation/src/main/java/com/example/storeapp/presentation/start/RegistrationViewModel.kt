package com.example.storeapp.presentation.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.SaveUserModel
import com.example.domain.usecase.AddNewUserUseCase
import com.example.storeapp.presentation.start.state.StartState
import com.example.storeapp.userModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val addNewUserUseCase: AddNewUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<StartState>(StartState.Success)
    val state = _state.asStateFlow()

    fun onEnterClick(param: SaveUserModel) {
        viewModelScope.launch {
            userModel = param
            addNewUserUseCase.execute(param)
            _state.value = StartState.Loading
        }
    }
}