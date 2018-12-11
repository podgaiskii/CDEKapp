package by.marpod.cdekapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import by.marpod.cdekapp.constants.FirebaseTables
import by.marpod.cdekapp.data.dto.Request
import by.marpod.cdekapp.util.FirebaseDatabaseLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class RequestRepository @Inject constructor(
        @Named(FirebaseTables.REQUESTS) private val requestsDatabaseReference: DatabaseReference
) {

    fun getAll(): LiveData<List<Request>?> = object : FirebaseDatabaseLiveData<List<Request>?>(
            requestsDatabaseReference.orderByKey()
    ) {
        override fun onCancelled(error: DatabaseError) {
            postValue(null)
        }

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                val result = mutableListOf<Request>()
                for (child in dataSnapshot.children) {
                    result += child.getValue(Request::class.java)!!
                }
                postValue(result)
            } else {
                postValue(null)
            }
        }
    }

    fun get(id: String): LiveData<Request?> = object : FirebaseDatabaseLiveData<Request?>(
            requestsDatabaseReference.orderByChild(Request.FIELD_ID).equalTo(id)
    ) {
        override fun onCancelled(error: DatabaseError) {
            postValue(null)
        }

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                postValue(dataSnapshot.children.iterator().next().getValue(Request::class.java))
            } else {
                postValue(null)
            }
        }
    }

    fun add(request: Request): LiveData<Request?> {
        val result = MutableLiveData<Request?>()
        requestsDatabaseReference.push()
                .apply {
                    result.value = key?.let {
                        val newRequest = with(request) {
                            Request(
                                    it,
                                    cityFrom,
                                    cityTo,
                                    volume,
                                    date,
                                    userId
                            )
                        }
                        setValue(newRequest, null)
                        newRequest
                    }
                }
        return result
    }
}