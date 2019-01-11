package by.marpod.cdekapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CalculatedRequest(
        val id: String = "",
        val count: Int = 0,
        val request: Request? = Request(),
        val directions: Map<Int, List<Direction>> = mapOf(),
        val directionsIds: HashMap<Int, List<String>> = hashMapOf()
) : Parcelable {

    companion object {
        const val FIELD_ID = "id"
        const val FIELD_COUNT = "count"
    }
}