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
import by.marpod.cdekapp.util.extensions.EventObserver
import by.marpod.cdekapp.util.extensions.areValid
import by.marpod.cdekapp.util.extensions.text
import by.marpod.cdekapp.viewmodel.RegistrationViewModel
import kotlinx.android.synthetic.main.fragment_registration.*
import javax.inject.Inject

class RegistrationFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var currentUserRepository: CurrentUserRepository

    override val layout: Int
        get() = R.layout.fragment_registration

    lateinit var viewModel: RegistrationViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RegistrationViewModel::class.java)

        btn_log_in.setOnClickListener {
            findNavController().navigateUp()
        }

        btn_sign_up.setOnClickListener {
            if (allViewsValid) {
                viewModel.register(User("", username.text, password.text))
            }
        }

        viewModel.showError.observe(this, EventObserver {
            showError(it)
        })

        viewModel.registrationSuccessful.observe(this, EventObserver {
            currentUserRepository.put(it)
            findNavController().navigate(R.id.action_registrationFragment_to_mainActivity)
            activity!!.finish()
        })
    }

    private val allViewsValid: Boolean
        get() {
            var allViewsValid = listOf(username, password, repeat_password).areValid()
            if (allViewsValid && password.text != repeat_password.text) {
                showError(R.string.error_password_mismatch)
                allViewsValid = false
            }
            return allViewsValid
        }
}