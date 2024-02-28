package com.example.storeapp.presentation.menu.profile

import com.example.data.repository.ProductRepositoryImpl
import com.example.domain.usecase.ExitFromAppUseCase
import kotlinx.coroutines.Dispatchers
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

class ProfileViewModelTest {
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val exitFromAppUseCase = mock<ExitFromAppUseCase>()
    private val productRepositoryImpl = mock<ProductRepositoryImpl>()
    private lateinit var viewModel: ProfileViewModel

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = ProfileViewModel(productRepositoryImpl, exitFromAppUseCase)
    }

    @Test
    fun isExitFromApp() {
        runBlocking {
            launch(Dispatchers.Main) {
                viewModel.exitFromApp()
            }
        }

        Mockito.verify(exitFromAppUseCase, Mockito.times(1)).execute()
    }
}