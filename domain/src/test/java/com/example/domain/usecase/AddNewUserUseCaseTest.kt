package com.example.domain.usecase

import com.example.domain.model.SaveUserModel
import com.example.domain.repository.UserRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class AddNewUserUseCaseTest {

    private val userRepository = mock<UserRepository>()

    @Test
    fun isAddNewUser() {
        val saveUserModel = SaveUserModel(
            firstName = "First",
            lastName = "Last",
            phoneNumber = "+1 111 111 11 11"
        )
        val addNewUserUseCase = AddNewUserUseCase(userRepository = userRepository)

        addNewUserUseCase.execute(param = saveUserModel)

        Mockito.verify(userRepository).addNewUser(param = saveUserModel)
    }
}