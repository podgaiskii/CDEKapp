package by.marpod.cdekapp.data.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
        val id: String = "",
        val name: String = ""
) : Parcelable