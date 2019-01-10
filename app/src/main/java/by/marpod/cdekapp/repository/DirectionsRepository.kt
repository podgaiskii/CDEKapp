package by.marpod.cdekapp.repository

import androidx.lifecycle.LiveData
import by.marpod.cdekapp.constants.FirebaseTables
import by.marpod.cdekapp.data.dto.Direction
import by.marpod.cdekapp.util.FirebaseDatabaseLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class DirectionsRepository @Inject constructor(
        @Named(FirebaseTables.DIRECTIONS) private val directionsDatabaseReference: DatabaseReference
) {

    fun getAll(): LiveData<List<Direction>?> = object : FirebaseDatabaseLiveData<List<Direction>?>(
            directionsDatabaseReference.orderByKey()
    ) {
        override fun onCancelled(error: DatabaseError) {
            postValue(null)
        }

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                postValue(dataSnapshot.children.map { it.value as Direction })
            } else {
                postValue(null)
            }
        }
    }

    fun getAllFrom(cityFrom: String): LiveData<List<Direction>?> = object : FirebaseDatabaseLiveData<List<Direction>?>(
            directionsDatabaseReference.orderByKey()
    ) {
        override fun onCancelled(error: DatabaseError) {
            postValue(null)
        }

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                postValue(dataSnapshot.children
                        .map { it.getValue(Direction::class.java)!! }
                        .filter { it.hasCity(cityFrom) })
            } else {
                postValue(null)
            }
        }
    }

    fun addAll(directions: List<Direction>) {
        val map = directions.map {
            val key = directionsDatabaseReference.push().key!!
            key to Direction(key, it.firstCity, it.secondCity, it.method, it.distance, it.hours)
        }.toMap()
        directionsDatabaseReference.updateChildren(map)
    }
}