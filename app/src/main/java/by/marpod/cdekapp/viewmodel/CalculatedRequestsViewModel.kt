package by.marpod.cdekapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.marpod.cdekapp.data.CalculatedRequest
import by.marpod.cdekapp.data.Request
import by.marpod.cdekapp.repository.CalculatedRepository
import by.marpod.cdekapp.repository.DirectionsRepository
import by.marpod.cdekapp.util.Event
import by.marpod.cdekapp.util.extensions.switchMap
import javax.inject.Inject

class CalculatedRequestsViewModel @Inject constructor(
        private val calculatedRepository: CalculatedRepository,
        private val directionsRepository: DirectionsRepository
) : ViewModel() {

    private val requestGet = MutableLiveData<Request>()

    private val resultGet: LiveData<CalculatedRequest?> = requestGet.switchMap { request ->
        calculatedRepository.get(request)
    }

    private val _requestFound = MediatorLiveData<Event<CalculatedRequest>>()
    val requestFound: LiveData<Event<CalculatedRequest>>
        get() = _requestFound

    init {
        _requestFound.addSource(resultGet) {
            _requestFound.value = Event(it ?: CalculatedRequest(requestGet.value!!.id, request = requestGet.value!!))
        }
    }

    fun get(request: Request) {
        requestGet.value = request
    }

    fun add(index: Int, calculatedRequest: CalculatedRequest) {
        calculatedRepository.add(index, calculatedRequest)
    }
}