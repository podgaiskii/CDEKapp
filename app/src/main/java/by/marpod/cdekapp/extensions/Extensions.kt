package by.marpod.cdekapp.extensions

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.LayoutRes
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DataSnapshot

fun ViewGroup.inflate(@LayoutRes layout: Int) = LayoutInflater.from(context).inflate(layout, this, false)

var TextInputLayout.text: String
    get() = editText!!.text.toString()
    set(value) = editText!!.setText(value)

fun TextInputLayout.isEmpty() = text.isEmpty()
fun TextInputLayout.isNotEmpty() = text.isNotEmpty()
fun TextInputLayout.isBlank() = text.isBlank()
fun TextInputLayout.isNotBlank() = text.isNotBlank()

val TextInputLayout.autocomplete
    get() = editText as AutoCompleteTextView

fun <T> DataSnapshot.toListOf(clazz: Class<T>): List<T>? = children.map { it.getValue(clazz)!! }