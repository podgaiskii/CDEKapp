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

    fun hasCity(city: String) = firstCity == city || secondCity == city

    fun calculateCost(size: Int): Int {
        val k = when (method) {
            TransportationMethod.PLANE -> 0.8
            TransportationMethod.AUTO -> 0.6
            TransportationMethod.TRAIN -> 0.4
            TransportationMethod.SHIP -> 0.2
            else -> throw IllegalStateException("unknown transportation method: $method")
        }
        return (size * k + hours / k).toInt()
    }
}