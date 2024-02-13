package com.example.storeapp.presentation.utilities

import android.content.Intent
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import com.example.storeapp.APP_ACTIVITY
import com.example.storeapp.MainActivity
import com.example.storeapp.R
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

fun View.makeEnabled() {
    isEnabled = true
}

fun View.makeDisabled() {
    isEnabled = false
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

fun setupPhoneMask(textView: TextView) {
    val slots = UnderscoreDigitSlotsParser().parseSlots("+7 ___ ___ __ __")
    val mask = MaskImpl.createTerminated(slots)
    mask.isHideHardcodedHead = true
    mask.isForbidInputWhenFilled = true
    val formatWatcher: FormatWatcher = MaskFormatWatcher(mask)
    formatWatcher.installOn(textView)
}

fun setupUnderlineForText(textView: TextView) {
    textView.paintFlags = Paint.UNDERLINE_TEXT_FLAG
}

fun selectFormCountFavoriteProducts(count: Int): String {
    var result = count % 100
    if (result in 10..20) {
        return "товаров"
    }

    result = count % 10
    if (result == 0 || result > 4) {
        return "товаров"
    }
    if (result > 1) {
        return "товара"
    }
    return if (result == 1) {
        "товар"
    } else ""
}

fun selectFormCountProducts(count: Int): String {
    var result = count % 100
    if (result in 10..20) {
        return "штук"
    }

    result = count % 10
    if (result == 0 || result > 4) {
        return "штук"
    }
    if (result > 1) {
        return "штуки"
    }
    return if (result == 1) {
        "штука"
    } else ""
}

fun selectFormCountReview(count: Int): String {
    var result = count % 100
    if (result in 10..20) {
        return "отзывов"
    }

    result = count % 10
    if (result == 0 || result > 4) {
        return "отзывов"
    }
    if (result > 1) {
        return "отзыва"
    }
    return if (result == 1) {
        "отзыв"
    } else ""
}

fun getImageList(id: String): List<Int> {
    val imageList: List<Int>

    when (id) {
        "cbf0c984-7c6c-4ada-82da-e29dc698bb50" -> imageList =
            listOf(R.drawable.img_f, R.drawable.img_e)

        "54a876a5-2205-48ba-9498-cfecff4baa6e" -> imageList =
            listOf(R.drawable.img_a, R.drawable.img_b)

        "75c84407-52e1-4cce-a73a-ff2d3ac031b3" -> imageList =
            listOf(R.drawable.img_e, R.drawable.img_f)

        "16f88865-ae74-4b7c-9d85-b68334bb97db" -> imageList =
            listOf(R.drawable.img_c, R.drawable.img_d)

        "26f88856-ae74-4b7c-9d85-b68334bb97db" -> imageList =
            listOf(R.drawable.img_b, R.drawable.img_c)

        "15f88865-ae74-4b7c-9d81-b78334bb97db" -> imageList =
            listOf(R.drawable.img_f, R.drawable.img_a)

        "88f88865-ae74-4b7c-9d81-b78334bb97db" -> imageList =
            listOf(R.drawable.img_d, R.drawable.img_c)

        else -> imageList =
            listOf(R.drawable.img_a, R.drawable.img_e)
    }

    return imageList
}

fun restartActivity() {
    APP_ACTIVITY.finish()
    val intent = Intent(APP_ACTIVITY, MainActivity::class.java)
    APP_ACTIVITY.startActivity(intent)
}