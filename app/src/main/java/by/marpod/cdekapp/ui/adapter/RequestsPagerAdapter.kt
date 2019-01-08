package by.marpod.cdekapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import by.marpod.cdekapp.base.BaseFragment

class RequestsPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val fragments = mutableListOf<BaseFragment>()
    private val tabs = mutableListOf<String>()

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount() = fragments.size

    fun addFragment(fragment: BaseFragment, tab: String) {
        if (tab !in tabs) {
            tabs += tab
        }
        if (fragment !in fragments) {
            fragments += fragment
        }
    }

    override fun getPageTitle(position: Int) = tabs[position]
}