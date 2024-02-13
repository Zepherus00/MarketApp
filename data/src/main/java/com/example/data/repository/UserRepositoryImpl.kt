package com.example.data.repository

import com.example.data.room.Dependencies.userDao
import com.example.data.utilities.toUser
import com.example.domain.model.SaveUserModel
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepositoryImpl : UserRepository {
    override fun addNewUser(param: SaveUserModel) {
        val saveUser = param.toUser()
        CoroutineScope(Dispatchers.IO).launch {
            userDao.insert(saveUser)
        }
    }

    override fun clearTable() {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.clearTable()
        }
    }
}