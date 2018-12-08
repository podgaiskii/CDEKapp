package by.marpod.cdekapp.util

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

abstract class FirebaseDatabaseLiveData<T>(query: Query) : MutableLiveData<T>(), ValueEventListener {

    init {
        query.addListenerForSingleValueEvent(this)
    }

    abstract override fun onCancelled(error: DatabaseError)
    abstract override fun onDataChange(dataSnapshot: DataSnapshot)
}