package by.marpod.cdekapp.ui.fragment.moder

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import by.marpod.cdekapp.R
import by.marpod.cdekapp.base.BaseFragment
import by.marpod.cdekapp.data.Request
import by.marpod.cdekapp.ui.activity.HandleRequestActivity
import by.marpod.cdekapp.ui.adapter.recyclerview.IncomeRequestsAdapter
import by.marpod.cdekapp.util.extensions.EventObserver
import by.marpod.cdekapp.viewmodel.CalculatedRequestsViewModel
import by.marpod.cdekapp.viewmodel.RequestsViewModel
import kotlinx.android.synthetic.main.fragment_income_requests.*
import javax.inject.Inject

class IncomeRequestsFragment @Inject constructor() : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layout: Int
        get() = R.layout.fragment_income_requests

    lateinit var viewModel: RequestsViewModel
    lateinit var calculatedRequestsViewModel: CalculatedRequestsViewModel

    private var adapter = IncomeRequestsAdapter(onClick = { handleRequest(it) })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RequestsViewModel::class.java)
        calculatedRequestsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(CalculatedRequestsViewModel::class.java)

        recycler_view.adapter = adapter

        refresh.setOnClickListener { update() }

        viewModel.requestsFound.observe(this, EventObserver { items ->
            adapter.items = items
            hideProgress()
        })

        viewModel.noRequestsFound.observe(this, EventObserver {
            showEmptyList()
        })

        calculatedRequestsViewModel.requestFound.observe(this, EventObserver {
            val args = bundleOf(
                    HandleRequestActivity.EXTRA_REQUEST to it.request,
                    HandleRequestActivity.EXTRA_INDEX to it.count
            )
            hideProgress()
            findNavController().navigate(R.id.action_moderActivity_to_handleRequestActivity, args)
        })
    }

    override fun onResume() {
        super.onResume()
        update()
    }

    private fun update() {
        hideEmptyList()
        showProgress()
        viewModel.getAll()
    }

    private fun showEmptyList() {
        hideProgress()
        empty_list.isVisible = true
    }

    private fun hideEmptyList() {
        empty_list.isGone = true
    }

    private fun handleRequest(request: Request) {
        calculatedRequestsViewModel.get(request)
        showProgress()
    }

    private fun showProgress() {
        progress.isVisible = true
    }

    private fun hideProgress() {
        progress.isGone = true
    }
}