package by.marpod.cdekapp.ui.fragment.moder

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import by.marpod.cdekapp.R
import by.marpod.cdekapp.base.BaseFragment
import by.marpod.cdekapp.data.Request
import by.marpod.cdekapp.ui.adapter.recyclerview.IncomeRequestsAdapter
import by.marpod.cdekapp.ui.adapter.viewpager.CalculatedPagerAdapter
import by.marpod.cdekapp.ui.fragment.CalculatedRequestFragment
import by.marpod.cdekapp.util.extensions.EventObserver
import by.marpod.cdekapp.viewmodel.CalculatedRequestsViewModel
import by.marpod.cdekapp.viewmodel.RequestsViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.fragment_handled_requests.*
import javax.inject.Inject

class HandledRequestsFragment @Inject constructor() : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layout: Int
        get() = R.layout.fragment_handled_requests

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>

    lateinit var viewModel: RequestsViewModel
    lateinit var calculatedRequestsViewModel: CalculatedRequestsViewModel

    private var adapter = IncomeRequestsAdapter(onClick = { showAvailableRoutes(it) })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RequestsViewModel::class.java)
        calculatedRequestsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(CalculatedRequestsViewModel::class.java)

        recycler_view.adapter = adapter

        refresh.setOnClickListener { update() }

        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet).apply {
            isHideable = true
        }
        hideBottomSheet()

        viewModel.requestsFound.observe(this, EventObserver { items ->
            adapter.items = items
            hideProgress()
        })

        viewModel.noRequestsFound.observe(this, EventObserver { showEmptyList() })

        calculatedRequestsViewModel.requestFound.observe(this, EventObserver { request ->
            pager.adapter = CalculatedPagerAdapter(
                    activity!!.supportFragmentManager,
                    List(request.count) { index ->
                        CalculatedRequestFragment.newInstance(request.request!!, index, request.directionsIds)
                    })
            directions_dots.setupWithViewPager(pager)
            showBottomSheet()
        })
    }

    private fun showAvailableRoutes(request: Request) {
        calculatedRequestsViewModel.get(request)
    }

    override fun onResume() {
        super.onResume()
        update()
    }

    private fun update() {
        hideEmptyList()
        showProgress()
        viewModel.getAllHandled()
    }

    private fun showEmptyList() {
        hideProgress()
        empty_list.isVisible = true
    }

    private fun hideEmptyList() {
        empty_list.isGone = true
    }

    private fun showProgress() {
        progress.isVisible = true
    }

    private fun hideProgress() {
        progress.isGone = true
    }

    private fun showBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }

    private fun hideBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }
}