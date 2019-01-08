package by.marpod.cdekapp.ui.fragment.moder

import android.os.Bundle
import android.view.View
import by.marpod.cdekapp.R
import by.marpod.cdekapp.base.BaseFragment
import by.marpod.cdekapp.ui.adapter.RequestsPagerAdapter
import kotlinx.android.synthetic.main.fragment_with_tabs.*
import javax.inject.Inject

class ModerMainFragment : BaseFragment() {

    @Inject
    lateinit var incomeRequestsFragment: IncomeRequestsFragment

    @Inject
    lateinit var handledRequestsFragment: HandledRequestsFragment

    override val layout: Int
        get() = R.layout.fragment_with_tabs

    private lateinit var pagerAdapter: RequestsPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pagerAdapter = RequestsPagerAdapter(activity!!.supportFragmentManager)
        pager.adapter = pagerAdapter.apply {
            addFragment(incomeRequestsFragment, getString(R.string.income_requests))
            addFragment(handledRequestsFragment, getString(R.string.handled_requests))
        }
        tabs.setupWithViewPager(pager)
    }
}