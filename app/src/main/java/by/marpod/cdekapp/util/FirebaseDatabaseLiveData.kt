package by.marpod.cdekapp.util

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

abstract class FirebaseDatabaseLiveData<T> : MutableLiveData<T>(), ValueEventListener {

    abstract override fun onCancelled(error: DatabaseError)
    abstract override fun onDataChange(dataSnapshot: DataSnapshot)
}