package by.marpod.cdekapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {

    protected abstract val layout: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(layout, container, false)!!

    fun showError(@StringRes message: Int) {
        (activity as BaseActivity).showError(message)
    }

    fun showError(message: String) {
        (activity as BaseActivity).showError(message)
    }
}