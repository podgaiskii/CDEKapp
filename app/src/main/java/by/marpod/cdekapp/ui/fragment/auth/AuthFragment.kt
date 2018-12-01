package by.marpod.cdekapp.ui.fragment.auth

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import by.marpod.cdekapp.R
import by.marpod.cdekapp.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_auth.*

class AuthFragment : BaseFragment() {

    override val layout: Int
        get() = R.layout.fragment_auth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_sign_up.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_registrationFragment)
        }
    }
}