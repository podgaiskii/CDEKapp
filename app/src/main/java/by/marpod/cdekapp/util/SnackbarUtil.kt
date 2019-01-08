package by.marpod.cdekapp.util

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import by.marpod.cdekapp.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

object SnackbarUtil {

    fun showSnackbar(rootView: View?,
                     context: Context?,
                     message: String,
                     @BaseTransientBottomBar.Duration duration: Int,
                     actionText: String? = null,
                     actionListener: View.OnClickListener? = null): Snackbar? {
        rootView ?: return null
        context ?: return null

        var snackbar: Snackbar? = null
        try {
            snackbar = Snackbar.make(rootView, message, duration)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return snackbar?.apply {
            actionListener?.let { listener ->
                setAction(actionText, listener)
            }
            actionText?.let {
                setActionTextColor(ContextCompat.getColor(context, R.color.colorAccent))
            }
            show()
        }
    }
}