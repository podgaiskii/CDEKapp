package by.marpod.cdekapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import by.marpod.cdekapp.constants.FirebaseTables
import by.marpod.cdekapp.data.CalculatedRequest
import by.marpod.cdekapp.data.Request
import by.marpod.cdekapp.util.FirebaseDatabaseLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject
import javax.inject.Named

class CalculatedRepository @Inject constructor(
        @Named(FirebaseTables.CALCULATED) private val calculatedDatabaseReference: DatabaseReference
) {

    fun get(request: Request): LiveData<CalculatedRequest?> = object : FirebaseDatabaseLiveData<CalculatedRequest?>(
            calculatedDatabaseReference.orderByKey().equalTo(request.id)
    ) {
        override fun onCancelled(error: DatabaseError) {
            postValue(null)
        }

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                val requestDataSnapshot = dataSnapshot.child(request.id)
                val count = requestDataSnapshot.child(CalculatedRequest.FIELD_COUNT).getValue(Int::class.java)!!
                val directionsIdsMap = hashMapOf<Int, List<String>>()
                for (index in 0..(count - 1)) {
                    val directionIds = requestDataSnapshot.child(index.toString())
                            .getValue(String::class.java)!!
                            .split(",")
                    directionsIdsMap[index] = directionIds
                }
                postValue(CalculatedRequest(request.id, count, request, directionsIds = directionsIdsMap))
            } else {
                postValue(null)
            }
        }
    }

    fun add(index: Int, calculatedRequest: CalculatedRequest): LiveData<CalculatedRequest> {
        val result = MutableLiveData<CalculatedRequest>()
        val map = mutableMapOf(CalculatedRequest.FIELD_COUNT to index + 1,
                index.toString() to calculatedRequest.directions[index]!!.joinToString(",") { it.id })
        calculatedDatabaseReference.child(calculatedRequest.id).updateChildren(map)
        result.value = calculatedRequest
        return result
    }
}