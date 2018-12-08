package by.marpod.cdekapp.data.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Route(
        val route: List<City>,
        val days: Int,
        val cost: Float
) : Parcelable