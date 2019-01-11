package by.marpod.cdekapp.ui.adapter.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import by.marpod.cdekapp.ui.fragment.CalculatedRequestFragment

class CalculatedPagerAdapter(fragmentManager: FragmentManager,
                             private var fragments: List<CalculatedRequestFragment>) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size
}