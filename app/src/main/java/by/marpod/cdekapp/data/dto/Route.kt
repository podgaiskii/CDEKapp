package by.marpod.cdekapp.data.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Route(
        val id: String = "",
        val route: List<City> = listOf(),
        val days: Int = 0,
        val cost: Float = 0F
) : Parcelable