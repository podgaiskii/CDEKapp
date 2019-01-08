package by.marpod.cdekapp.ui.activity

import android.view.View
import by.marpod.cdekapp.R
import by.marpod.cdekapp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    override val layout: Int
        get() = R.layout.activity_splash

    override val rootView: View
        get() = root
}