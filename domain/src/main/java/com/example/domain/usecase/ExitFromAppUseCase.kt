package com.example.domain.usecase

import com.example.domain.repository.ProductRepository
import com.example.domain.repository.UserRepository

class ExitFromAppUseCase(
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository,
) {

    fun execute() {
        productRepository.clearTable()
        userRepository.clearTable()
    }
}