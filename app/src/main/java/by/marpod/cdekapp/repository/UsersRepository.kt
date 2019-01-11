package by.marpod.cdekapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import by.marpod.cdekapp.constants.FirebaseTables
import by.marpod.cdekapp.data.User
import by.marpod.cdekapp.util.FirebaseDatabaseLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(
        @Named(FirebaseTables.USERS) private val usersDatabaseReference: DatabaseReference
) {

    fun get(username: String): LiveData<User?> = object : FirebaseDatabaseLiveData<User?>(
            usersDatabaseReference.orderByChild(User.FIELD_USERNAME).equalTo(username)
    ) {
        override fun onCancelled(error: DatabaseError) {
            postValue(null)
        }

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                postValue(dataSnapshot.children.iterator().next().getValue(User::class.java))
            } else {
                postValue(null)
            }
        }
    }

    fun add(user: User): LiveData<User?> {
        val result = MutableLiveData<User?>()
        usersDatabaseReference.push()
                .apply {
                    result.value = key?.let {
                        val newUser = with(user) {
                            User(
                                    it,
                                    username,
                                    password,
                                    role
                            )
                        }
                        setValue(newUser, null)
                        newUser
                    }
                }
        return result
    }
}