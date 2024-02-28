package com.example.domain.usecase

import com.example.domain.repository.ProductRepository
import com.example.domain.repository.UserRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class ExitFromAppUseCaseTest {
    private val productRepository = mock<ProductRepository>()
    private val userRepository = mock<UserRepository>()

    @Test
    fun isExitFromApp() {
        val exitFromAppUseCase = ExitFromAppUseCase(
            productRepository = productRepository,
            userRepository = userRepository
        )

        exitFromAppUseCase.execute()

        Mockito.verify(productRepository).clearTable()
        Mockito.verify(userRepository).clearTable()
    }
}