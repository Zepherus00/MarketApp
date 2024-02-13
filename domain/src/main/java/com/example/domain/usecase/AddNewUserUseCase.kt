package com.example.domain.usecase

import com.example.domain.model.SaveUserModel
import com.example.domain.repository.UserRepository

class AddNewUserUseCase(private val userRepository: UserRepository) {
    fun execute(param: SaveUserModel) {
        userRepository.addNewUser(param)
    }
}