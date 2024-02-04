package com.example.storeapp.entity.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM user WHERE phoneNumber LIKE :phone")
    suspend fun getUser(phone: String): User

    @Query("DELETE FROM user")
    suspend fun clearTable()
}