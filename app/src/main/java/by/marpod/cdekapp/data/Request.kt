package by.marpod.cdekapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Request(
        val id: String = "",
        val cityFrom: String = "",
        val cityTo: String = "",
        val length: Int = 0,
        val width: Int = 0,
        val height: Int = 0,
        val date: Long = 0L,
        val username: String = "",
        val handled: Boolean = false
) : Parcelable {

    companion object {
        const val FIELD_ID = "id"
        const val FIELD_CITY_FROM = "cityFrom"
        const val FIELD_CITY_TO = "cityTo"
        const val FIELD_LENGTH = "length"
        const val FIELD_WIDTH = "width"
        const val FIELD_HEIGHT = "height"
        const val FIELD_DATE = "date"
        const val FIELD_USERNAME = "username"
        const val FIELD_HANDLED = "handled"
    }

    fun getSize() = length * width * height
}