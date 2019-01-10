package by.marpod.cdekapp.ui.fragment.auth

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import by.marpod.cdekapp.R
import by.marpod.cdekapp.base.BaseFragment
import by.marpod.cdekapp.data.dto.User
import by.marpod.cdekapp.repository.CurrentUserRepository
import by.marpod.cdekapp.repository.DirectionsRepository
import by.marpod.cdekapp.util.extensions.EventObserver
import by.marpod.cdekapp.util.extensions.areValid
import by.marpod.cdekapp.util.extensions.text
import by.marpod.cdekapp.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_auth.*
import javax.inject.Inject

class AuthFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var currentUserRepository: CurrentUserRepository

    @Inject
    lateinit var directionsRepository: DirectionsRepository

    override val layout: Int
        get() = R.layout.fragment_auth

    lateinit var viewModel: AuthViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(AuthViewModel::class.java)

        btn_sign_up.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_registrationFragment)
        }

        btn_log_in.setOnClickListener {
            if (allViewsValid) {
                viewModel.authorize(username.text)
            }
        }

        viewModel.showError.observe(this, EventObserver {
            showError(it)
        })

        viewModel.userFound.observe(this, EventObserver {
            if (it.password == password.text) {
                currentUserRepository.put(it)
                when (it.role) {
                    User.ROLE_MODER -> findNavController().navigate(R.id.action_authFragment_to_moderActivity)
                    else -> findNavController().navigate(R.id.action_authFragment_to_mainActivity)
                }
                activity!!.finish()
            } else {
                showError(R.string.error_auth)
            }
        })
    }

    private val allViewsValid: Boolean
        get() = listOf(username, password).areValid()
}