package by.marpod.cdekapp.ui.fragment.moder

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import by.marpod.cdekapp.R
import by.marpod.cdekapp.base.BaseFragment
import by.marpod.cdekapp.data.dto.Request
import by.marpod.cdekapp.ui.adapter.recyclerview.IncomeRequestsAdapter
import by.marpod.cdekapp.util.extensions.EventObserver
import by.marpod.cdekapp.viewmodel.RequestsViewModel
import kotlinx.android.synthetic.main.fragment_handled_requests.*
import javax.inject.Inject

class HandledRequestsFragment @Inject constructor() : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layout: Int
        get() = R.layout.fragment_handled_requests

    lateinit var viewModel: RequestsViewModel

    private var adapter = IncomeRequestsAdapter(onClick = { showAvailableRoutes(it) })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RequestsViewModel::class.java)

        recycler_view.adapter = adapter

        refresh.setOnClickListener { update() }

        viewModel.requestsFound.observe(this, EventObserver { items ->
            adapter.items = items
            hideProgress()
        })

        viewModel.noRequestsFound.observe(this, EventObserver { showEmptyList() })
    }

    private fun showAvailableRoutes(request: Request) {
        //TODO: implement
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
}