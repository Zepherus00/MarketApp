package com.example.storeapp.presentation.utilities

import android.text.Editable
import android.text.TextWatcher

class AppOnTextChangedWatcher(val onSuccess: (CharSequence?) -> Unit) : TextWatcher {

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        onSuccess(p0)
    }

    override fun afterTextChanged(s: Editable?) {}
}