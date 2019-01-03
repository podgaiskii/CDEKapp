package by.marpod.cdekapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.marpod.cdekapp.R
import by.marpod.cdekapp.data.dto.Request
import by.marpod.cdekapp.repository.RequestRepository
import by.marpod.cdekapp.util.Event
import by.marpod.cdekapp.util.extensions.switchMap
import javax.inject.Inject

class RequestsViewModel @Inject constructor(
        private val requestRepository: RequestRepository,
        private val context: Context
) : ViewModel() {

    private val requestGet = MutableLiveData<String>()

    private val resultGet: LiveData<Request?> = requestGet.switchMap { requestId ->
        requestRepository.get(requestId)
    }

    private val _requestFound = MediatorLiveData<Event<Request>>()
    val requestFound: LiveData<Event<Request>>
        get() = _requestFound

    private val requestsGetAll = MutableLiveData<Unit>()

    private val resultGetAll: LiveData<List<Request>?> = requestsGetAll.switchMap { requestRepository.getAll() }

    private val requestsGetAllFor = MutableLiveData<String>()

    private val resultGetAllFor: LiveData<List<Request>?> = requestsGetAllFor.switchMap { username ->
        requestRepository.getAllFor(username)
    }

    private val _requestsFound = MediatorLiveData<Event<List<Request>?>>()
    val requestsFound: LiveData<Event<List<Request>?>>
        get() = _requestsFound

    private val requestAdd = MutableLiveData<Request>()

    private val resultAdd: LiveData<Request?> = requestAdd.switchMap { request ->
        requestRepository.add(request)
    }

    private val _requestAdded = MediatorLiveData<Event<Request>>()
    val requestAdded: LiveData<Event<Request>>
        get() = _requestAdded

    private val _showError = MediatorLiveData<Event<String>>()
    val showError: LiveData<Event<String>>
        get() = _showError

    init {
        _showError.addSource(resultAdd) { request ->
            if (request == null) {
                _showError.value = Event(context.getString(R.string.error_add_request))
            }
        }

        _showError.addSource(resultGet) { request ->
            if (request == null) {
                _showError.value = Event(context.getString(R.string.error_request_not_found))
            }
        }

        _requestFound.addSource(resultGet) { request ->
            request?.let {
                _requestFound.value = Event(it)
            }
        }

        _requestsFound.addSource(resultGetAll) { requests ->
            requests?.let {
                _requestsFound.value = Event(it)
            }
        }

        _requestsFound.addSource(resultGetAllFor) { requests ->
            requests?.let {
                _requestsFound.value = Event(it)
            }
        }

        _requestAdded.addSource(resultAdd) { request ->
            request?.let {
                _requestAdded.value = Event(it)
            }
        }
    }

    fun getRequest(requestId: String) {
        requestGet.value = requestId
    }

    fun getAll() {
        requestsGetAll.value = Unit
    }

    fun getAllFor(username: String) {
        requestsGetAllFor.value = username
    }

    fun addRequest(request: Request) {
        requestAdd.value = request
    }
}