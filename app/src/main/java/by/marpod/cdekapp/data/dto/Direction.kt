package by.marpod.cdekapp.data.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Direction(
        val id: String = "",
        val firstCity: String = "",
        val secondCity: String = "",
        val method: String = "",
        val distance: Int = 0,
        val hours: Int = 0
) : Parcelable {

    companion object {
        const val FIELD_ID = "id"
        const val FIELD_FIRST_CITY = "firstCity"
        const val FIELD_SECOND_CITY = "secondCity"
        const val FIELD_METHOD = "method"
        const val FIELD_DISTANCE = "distance"
        const val FIELD_HOURS = "hours"
    }
}