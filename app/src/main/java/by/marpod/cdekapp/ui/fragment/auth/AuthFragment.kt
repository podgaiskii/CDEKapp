package by.marpod.cdekapp.ui.fragment.auth

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import by.marpod.cdekapp.R
import by.marpod.cdekapp.base.BaseFragment
import by.marpod.cdekapp.extensions.EventObserver
import by.marpod.cdekapp.extensions.isBlank
import by.marpod.cdekapp.extensions.text
import by.marpod.cdekapp.repository.CurrentUserRepository
import by.marpod.cdekapp.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_auth.*
import javax.inject.Inject

class AuthFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var currentUserRepository: CurrentUserRepository

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
            var allViewsValid = true
            if (username.isBlank()) {
                username.error = getString(R.string.error_empty_field)
                allViewsValid = false
            }
            if (password.isBlank()) {
                password.error = getString(R.string.error_empty_field)
                allViewsValid = false
            }
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
                findNavController().navigate(R.id.action_authFragment_to_mainActivity)
            } else {
                showError(R.string.error_auth)
            }
        })
    }
}