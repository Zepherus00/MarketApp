package com.example.domain.repository

import com.example.domain.model.SaveUserModel

interface UserRepository {
    fun addNewUser(param: SaveUserModel)

    fun clearTable()
}