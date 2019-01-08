package by.marpod.cdekapp.ui.activity

import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.navigation.findNavController
import by.marpod.cdekapp.R
import by.marpod.cdekapp.base.BaseActivity
import by.marpod.cdekapp.repository.CurrentUserRepository
import kotlinx.android.synthetic.main.activity_moder.*
import javax.inject.Inject

class ModerActivity : BaseActivity() {

    @Inject
    lateinit var currentUserRepository: CurrentUserRepository

    override val layout: Int
        get() = R.layout.activity_moder

    override val rootView: View
        get() = root

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_logout -> {
                findNavController(R.id.moder_nav_host_fragment).navigate(R.id.action_moderActivity_to_authActivity)
                currentUserRepository.clear()
                finish()
                true
            }
            android.R.id.home -> {
                findNavController(R.id.moder_nav_host_fragment).navigateUp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}