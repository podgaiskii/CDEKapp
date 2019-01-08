package by.marpod.cdekapp.ui.activity

import android.view.View
import by.marpod.cdekapp.R
import by.marpod.cdekapp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : BaseActivity() {

    override val layout: Int
        get() = R.layout.activity_auth

    override val rootView: View
        get() = root
}