package by.marpod.cdekapp.repository

import androidx.lifecycle.LiveData
import by.marpod.cdekapp.constants.FirebaseTables
import by.marpod.cdekapp.data.Direction
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
                postValue(dataSnapshot.children.map { it.getValue(Direction::class.java)!! })
            } else {
                postValue(null)
            }
        }
    }

    fun getAll(ids: List<String>): LiveData<List<Direction>?> = object : FirebaseDatabaseLiveData<List<Direction>?>(
            directionsDatabaseReference.orderByKey()
    ) {
        override fun onCancelled(error: DatabaseError) {
            postValue(null)
        }

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                val directions = dataSnapshot.children.map { it.getValue(Direction::class.java)!! }
                val result = mutableListOf<Direction>()
                var prevCityTo = ""
                lateinit var direction: Direction
                for (id in ids) {
                    direction = with(directions.find { it.id == id }!!) {
                        if (prevCityTo.isNotBlank() && secondCity == prevCityTo) {
                            Direction(id, secondCity, firstCity, method, distance, hours)
                        } else this
                    }
                    result += direction
                    prevCityTo = direction.secondCity
                }
                postValue(result)
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
                        .filter { it.hasCity(cityFrom) }
                        .map {
                            if (it.secondCity == cityFrom) {
                                with(it) {
                                    Direction(id, secondCity, firstCity, method, distance, hours)
                                }
                            } else it
                        })
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