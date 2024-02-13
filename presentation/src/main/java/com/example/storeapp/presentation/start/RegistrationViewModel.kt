package com.example.storeapp.presentation.start

import androidx.lifecycle.ViewModel
import com.example.domain.model.SaveUserModel
import com.example.domain.usecase.AddNewUserUseCase
import com.example.storeapp.userModel

class RegistrationViewModel(
    private val addNewUserUseCase: AddNewUserUseCase
) : ViewModel() {

    fun addNewUser(param: SaveUserModel) {
        userModel = param
        addNewUserUseCase.execute(param)
    }
}