package by.marpod.cdekapp.ui.fragment

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import by.marpod.cdekapp.R
import by.marpod.cdekapp.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_splash.*

class SplashFragment : BaseFragment() {

    override val layout: Int
        get() = R.layout.fragment_splash

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(img_truck) {
            val anim = AnimationUtils.loadAnimation(context, R.anim.enter_from_left)
            startAnimation(anim)
            postDelayed({
                findNavController().navigate(R.id.action_splashActivity_to_authActivity)
            }, anim.duration)
        }
    }
}