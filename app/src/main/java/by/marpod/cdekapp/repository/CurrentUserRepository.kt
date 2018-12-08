package by.marpod.cdekapp.repository

import android.content.SharedPreferences
import by.marpod.cdekapp.data.dto.User
import javax.inject.Inject

class CurrentUserRepository @Inject constructor(
        private val mSharedPreferences: SharedPreferences
) {

    fun put(user: User) {
        mSharedPreferences.edit()
                .putString(User.FIELD_ID, user.id)
                .putString(User.FIELD_USERNAME, user.username)
                .putString(User.FIELD_PASSWORD, user.password)
                .apply()
    }

    fun get() = User(id, username, password)

    fun clear() {
        mSharedPreferences.edit()
                .putString(User.FIELD_ID, null)
                .putString(User.FIELD_USERNAME, null)
                .putString(User.FIELD_PASSWORD, null)
                .apply()
    }

    val id: String
        get() = mSharedPreferences.getString(User.FIELD_ID, "")!!

    val username: String
        get() = mSharedPreferences.getString(User.FIELD_USERNAME, "")!!

    val password: String
        get() = mSharedPreferences.getString(User.FIELD_PASSWORD, "")!!
}