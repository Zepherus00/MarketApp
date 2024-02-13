package com.example.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductModel(
    val id: String,
    val title: String,
    val subtitle: String,
    val price: Price,
    val feedback: Feedback?,
    val tags: List<String>,
    val available: Int,
    val description: String,
    val info: List<Info>,
    val ingredients: String
) : Parcelable

@Parcelize
data class Price(
    val price: String,
    val discount: Int,
    val priceWithDiscount: String,
    val unit: String
) : Parcelable

@Parcelize
data class Feedback(
    val count: Int,
    val rating: Double
) : Parcelable

@Parcelize
data class Info(
    val title: String,
    val value: String
) : Parcelable

data class ProductList(
    val items: List<ProductModel>
)