package com.example.data.room.product

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: Product)

    @Query("SELECT * FROM product")
    fun getProducts(): LiveData<List<Product>>

    @Query("DELETE FROM product")
    suspend fun clearTable()
}