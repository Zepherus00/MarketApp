@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.storeapp.presentation.start

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.example.domain.model.SaveUserModel
import com.example.domain.usecase.AddNewUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class RegistrationViewModelTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val addNewUserUseCase = mock<AddNewUserUseCase>()
    private lateinit var viewModel: RegistrationViewModel

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = RegistrationViewModel(addNewUserUseCase)
    }

    @Test
    fun isAddNewUserANdChangeState() {
        val testParam = SaveUserModel(
            firstName = "First",
            lastName = "Last",
            phoneNumber = "+1 111 111 11 11"
        )
        runBlocking {
            launch(Dispatchers.Main) {
                viewModel.onEnterClick(testParam)
            }
        }

        Mockito.verify(addNewUserUseCase, Mockito.times(1))
            .execute(param = testParam)
    }
}