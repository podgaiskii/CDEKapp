package by.marpod.cdekapp.extensions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.google.android.material.textfield.TextInputLayout

fun ViewGroup.inflate(@LayoutRes layout: Int) = LayoutInflater.from(context).inflate(layout, this, false)

var TextInputLayout.text: String
    get() = editText!!.text.toString()
    set(value) = editText!!.setText(value)

fun TextInputLayout.isEmpty() = text.isEmpty()
fun TextInputLayout.isNotEmpty() = text.isNotEmpty()
fun TextInputLayout.isBlank() = text.isBlank()
fun TextInputLayout.isNotBlank() = text.isNotBlank()