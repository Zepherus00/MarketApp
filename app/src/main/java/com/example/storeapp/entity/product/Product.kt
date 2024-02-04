package com.example.storeapp.entity.product

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey
    val id: String,
    val title: String,
    val subtitle: String,
    val price: String,
    val discount: Int,
    val priceWithDiscount: String,
    val unit: String,
    val feedbackCount: Int?,
    val feedbackRating: Double?
)