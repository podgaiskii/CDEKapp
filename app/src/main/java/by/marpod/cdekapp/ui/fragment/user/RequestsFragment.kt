package by.marpod.cdekapp.ui.fragment.user

import android.os.Bundle
import android.view.View
import by.marpod.cdekapp.R
import by.marpod.cdekapp.base.BaseFragment
import by.marpod.cdekapp.ui.adapter.RequestsPagerAdapter
import kotlinx.android.synthetic.main.fragment_with_tabs.*
import javax.inject.Inject

class RequestsFragment : BaseFragment() {

    @Inject
    lateinit var calculatedRequestsFragment: CalculatedRequestsFragment

    @Inject
    lateinit var sentRequestsFragment: SentRequestsFragment

    override val layout: Int
        get() = R.layout.fragment_with_tabs

    private lateinit var pagerAdapter: RequestsPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pagerAdapter = RequestsPagerAdapter(activity!!.supportFragmentManager)
        pager.adapter = pagerAdapter.apply {
            addFragment(calculatedRequestsFragment, getString(R.string.calculated_requests))
            addFragment(sentRequestsFragment, getString(R.string.sent_requests))
        }
        tabs.setupWithViewPager(pager)
    }

}