package by.marpod.cdekapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
        val id: String = "",
        val username: String = "",
        val password: String = "",
        val role: String = ""
) : Parcelable {

    companion object {
        const val FIELD_ID = "id"
        const val FIELD_USERNAME = "username"
        const val FIELD_PASSWORD = "password"
        const val FIELD_ROLE = "role"

        const val ROLE_MODER = "moder"
    }
}