package by.marpod.cdekapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.marpod.cdekapp.R
import by.marpod.cdekapp.data.User
import by.marpod.cdekapp.util.extensions.switchMap
import by.marpod.cdekapp.repository.UsersRepository
import by.marpod.cdekapp.util.Event
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
        usersRepository: UsersRepository,
        context: Context
) : ViewModel() {

    private val userRequested = MutableLiveData<User>()

    private val resultGet: LiveData<User?> = userRequested.switchMap { user ->
        usersRepository.get(user.username)
    }

    private val triggerPut = MediatorLiveData<User>()

    private val resultPut: LiveData<User?> = triggerPut.switchMap { user ->
        usersRepository.add(user)
    }

    private val _showError = MediatorLiveData<Event<String>>()
    val showError: LiveData<Event<String>>
        get() = _showError

    private val _registrationSuccessful = MediatorLiveData<Event<User>>()
    val registrationSuccessful: LiveData<Event<User>>
        get() = _registrationSuccessful

    init {
        _showError.addSource(resultGet) { user ->
            user?.let {
                _showError.value = Event(context.getString(R.string.error_user_already_exist))
            }
        }

        _showError.addSource(resultPut) { user ->
            if (user == null) {
                _showError.value = Event(context.getString(R.string.error_registration))
            }
        }

        triggerPut.addSource(resultGet) { user ->
            if (user == null) {
                triggerPut.value = userRequested.value
            }
        }

        _registrationSuccessful.addSource(resultPut) { user ->
            user?.let {
                _registrationSuccessful.value = Event(it)
            }
        }
    }

    fun register(user: User) {
        userRequested.value = user
    }
}