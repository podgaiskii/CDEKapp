package by.marpod.cdekapp.base

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import by.marpod.cdekapp.util.SnackbarUtil
import com.google.android.material.snackbar.BaseTransientBottomBar
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {

    protected abstract val layout: Int

    protected abstract val rootView: View

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layout)
    }

    fun showError(@StringRes message: Int) {
        SnackbarUtil.showSnackbar(rootView, this, getString(message), BaseTransientBottomBar.LENGTH_LONG)
    }

    fun showError(message: String) {
        SnackbarUtil.showSnackbar(rootView, this, message, BaseTransientBottomBar.LENGTH_LONG)
    }
}