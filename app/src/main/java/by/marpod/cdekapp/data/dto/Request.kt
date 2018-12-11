package by.marpod.cdekapp.data.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Request(
        val id: String,
        val cityFrom: String,
        val cityTo: String,
        val volume: Int,
        val date: Long,
        val userId: String
) : Parcelable {

    companion object {
        const val FIELD_ID = "id"
        const val FIELD_CITY_FROM = "cityFrom"
        const val FIELD_CITY_TO = "cityTo"
        const val FIELD_LENGTH = "length"
        const val FIELD_VOLUME = "volume"
        const val FIELD_USER_ID = "userId"
    }
}