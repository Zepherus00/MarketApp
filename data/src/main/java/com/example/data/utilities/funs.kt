package com.example.data.utilities

import com.example.data.model.Info
import com.example.data.model.ProductModel
import com.example.data.room.product.Product
import com.example.data.room.user.User
import com.example.domain.model.SaveFeedback
import com.example.domain.model.SaveInfo
import com.example.domain.model.SavePrice
import com.example.domain.model.SaveProductModel
import com.example.domain.model.SaveUserModel

fun SaveUserModel.toUser(): User {
    return User(
        firstName = this.firstName,
        lastName = this.lastName,
        phoneNumber = this.phoneNumber,
        isLoggedIn = true
    )
}

fun SaveProductModel.toProduct(): Product {
    return Product(
        id = this.id,
        title = this.title,
        subtitle = this.subtitle,
        price = this.price.price,
        discount = this.price.discount,
        priceWithDiscount = this.price.priceWithDiscount,
        unit = this.price.unit,
        feedbackCount = this.feedback?.count,
        feedbackRating = this.feedback?.rating
    )
}

fun ProductModel.toSaveProductModel(): SaveProductModel {
    val listInfoSave = mutableListOf<SaveInfo>()
    this.info.forEach {
        listInfoSave.add(it.toSaveInfo())
    }

    return SaveProductModel(
        id = this.id,
        title = this.title,
        subtitle = this.subtitle,
        price = SavePrice(
            price = this.price.price,
            discount = this.price.discount,
            priceWithDiscount = this.price.priceWithDiscount,
            unit = this.price.unit
        ),
        feedback = SaveFeedback(
            count = this.feedback?.count,
            rating = this.feedback?.rating
        ),
        tags = this.tags,
        available = this.available,
        description = this.description,
        info = listInfoSave,
        ingredients = this.ingredients
    )
}

fun Info.toSaveInfo(): SaveInfo {
    return SaveInfo(
        title = this.title,
        value = this.value
    )
}