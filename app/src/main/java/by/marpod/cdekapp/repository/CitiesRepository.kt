package by.marpod.cdekapp.repository

import androidx.lifecycle.LiveData
import by.marpod.cdekapp.constants.FirebaseTables
import by.marpod.cdekapp.data.dto.City
import by.marpod.cdekapp.util.FirebaseDatabaseLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class CitiesRepository @Inject constructor(
        @Named(FirebaseTables.CITIES) private val citiesDatabaseReference: DatabaseReference
) {

    fun getAll(): LiveData<List<City>?> = object : FirebaseDatabaseLiveData<List<City>?>(
            citiesDatabaseReference.orderByValue()
    ) {
        override fun onCancelled(error: DatabaseError) {
            postValue(null)
        }

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                postValue(dataSnapshot.children.map { City(it.key!!, it.value as String) })
            } else {
                postValue(null)
            }
        }
    }

    fun addAll(cities: List<City>) {
        val map = cities.map { citiesDatabaseReference.push().key!! to it.name }.toMap()
        citiesDatabaseReference.updateChildren(map)
    }
}