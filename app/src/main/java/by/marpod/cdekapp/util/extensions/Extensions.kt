package by.marpod.cdekapp.util.extensions

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.annotation.LayoutRes
import by.marpod.cdekapp.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DataSnapshot

fun ViewGroup.inflate(@LayoutRes layout: Int) = LayoutInflater.from(context).inflate(layout, this, false)

var TextInputLayout.text: String
    get() = editText!!.text.toString()
    set(value) = editText!!.setText(value)

var TextInputLayout.textInt: Int
    get() = text.toInt()
    set(value) = editText!!.setText(value)

fun TextInputLayout.isEmpty() = text.isEmpty()
fun TextInputLayout.isNotEmpty() = text.isNotEmpty()
fun TextInputLayout.isBlank() = text.isBlank()
fun TextInputLayout.isNotBlank() = text.isNotBlank()

fun TextInputLayout.isValid(): Boolean {
    if (isBlank()) {
        error = resources.getString(R.string.error_empty_field)
        return false
    } else if (isErrorEnabled) {
        isErrorEnabled = false
    }
    return true
}
fun TextInputLayout.setValid() {
    isErrorEnabled = false
}

fun List<TextInputLayout>.areValid(): Boolean {
    var areValid = true
    forEach {
        if (!it.isValid()) {
            areValid = false
        }
    }
    return areValid
}

val TextInputLayout.autocomplete
    get() = editText as AutoCompleteTextView

fun <T> DataSnapshot.toListOf(clazz: Class<T>): List<T>? = children.map { it.getValue(clazz)!! }