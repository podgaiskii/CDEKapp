package by.marpod.cdekapp.ui.fragment

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import by.marpod.cdekapp.R
import by.marpod.cdekapp.base.BaseFragment
import by.marpod.cdekapp.repository.CurrentUserRepository
import kotlinx.android.synthetic.main.fragment_splash.*
import javax.inject.Inject

class SplashFragment : BaseFragment() {

    @Inject
    lateinit var currentUserRepository: CurrentUserRepository

    override val layout: Int
        get() = R.layout.fragment_splash

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(img_truck) {
            val anim = AnimationUtils.loadAnimation(context, R.anim.enter_from_left)
            startAnimation(anim)
            postDelayed({
                if (currentUserRepository.id.isEmpty()) {
                    findNavController().navigate(R.id.action_splashActivity_to_authActivity)
                } else {
                    findNavController().navigate(R.id.action_splashActivity_to_mainActivity)
                }
                activity!!.finish()
            }, anim.duration)
        }
    }
}