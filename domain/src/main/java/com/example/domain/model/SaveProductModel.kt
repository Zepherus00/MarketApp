package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SaveProductModel(
    val id: String,
    val title: String,
    val subtitle: String,
    val price: SavePrice,
    val feedback: SaveFeedback?,
    val tags: List<String>,
    val available: Int,
    val description: String,
    val info: List<SaveInfo>,
    val ingredients: String
) : Parcelable

@Parcelize
data class SavePrice(
    val price: String,
    val discount: Int,
    val priceWithDiscount: String,
    val unit: String
) : Parcelable

@Parcelize
data class SaveFeedback(
    val count: Int?,
    val rating: Double?
) : Parcelable

@Parcelize
data class SaveInfo(
    val title: String,
    val value: String
) : Parcelable