package by.marpod.cdekapp.ui.fragment.auth

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import by.marpod.cdekapp.R
import by.marpod.cdekapp.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_registration.*

class RegistrationFragment : BaseFragment() {

    override val layout: Int
        get() = R.layout.fragment_registration

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_log_in.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}