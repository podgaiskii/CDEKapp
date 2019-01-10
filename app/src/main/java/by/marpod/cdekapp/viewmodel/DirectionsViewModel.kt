package by.marpod.cdekapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.marpod.cdekapp.data.dto.Direction
import by.marpod.cdekapp.repository.DirectionsRepository
import by.marpod.cdekapp.util.Event
import by.marpod.cdekapp.util.extensions.switchMap
import javax.inject.Inject

class DirectionsViewModel @Inject constructor(
        private val directionsRepository: DirectionsRepository
) : ViewModel() {

    private val requestGetAllFrom = MutableLiveData<String>()

    private val resultGetAllFrom: LiveData<List<Direction>?> = requestGetAllFrom.switchMap { cityFrom ->
        directionsRepository.getAllFrom(cityFrom)
    }

    private val _directionsFound = MediatorLiveData<Event<List<Direction>>>()
    val directionsFound: LiveData<Event<List<Direction>>>
        get() = _directionsFound

    init {
        _directionsFound.addSource(resultGetAllFrom) { items ->
            if (!items.isNullOrEmpty()) {
                _directionsFound.value = Event(items)
            }
        }
    }

    fun getAllFrom(cityFrom: String) {
        requestGetAllFrom.value = cityFrom
    }
}