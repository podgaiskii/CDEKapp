package by.marpod.cdekapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.marpod.cdekapp.R
import by.marpod.cdekapp.data.User
import by.marpod.cdekapp.repository.UsersRepository
import by.marpod.cdekapp.util.Event
import by.marpod.cdekapp.util.extensions.switchMap
import javax.inject.Inject

class AuthViewModel @Inject constructor(
        private val usersRepository: UsersRepository,
        private val context: Context
) : ViewModel() {

    private val requestedUser = MutableLiveData<String>()

    private val result: LiveData<User?> = requestedUser.switchMap { username ->
        usersRepository.get(username)
    }

    private val _showError = MediatorLiveData<Event<String>>()
    val showError: LiveData<Event<String>>
        get() = _showError

    private val _userFound = MediatorLiveData<Event<User>>()
    val userFound: LiveData<Event<User>>
        get() = _userFound

    init {
        _showError.addSource(result) { user ->
            if (user == null) {
                _showError.value = Event(context.getString(R.string.error_auth))
            }
        }

        _userFound.addSource(result) { user ->
            user?.let {
                _userFound.value = Event(it)
            }
        }
    }

    fun authorize(username: String) {
        requestedUser.value = username
    }
}