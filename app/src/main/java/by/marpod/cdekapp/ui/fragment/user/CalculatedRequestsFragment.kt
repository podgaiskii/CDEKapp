package by.marpod.cdekapp.ui.fragment.user

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import by.marpod.cdekapp.R
import by.marpod.cdekapp.base.BaseFragment
import by.marpod.cdekapp.data.dto.Request
import by.marpod.cdekapp.repository.CurrentUserRepository
import by.marpod.cdekapp.ui.activity.MainActivity
import by.marpod.cdekapp.ui.adapter.recyclerview.CalculatedRequestsAdapter
import by.marpod.cdekapp.util.extensions.EventObserver
import by.marpod.cdekapp.viewmodel.RequestsViewModel
import kotlinx.android.synthetic.main.fragment_calculated_requests.*
import javax.inject.Inject

class CalculatedRequestsFragment @Inject constructor() : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var currentUserRepository: CurrentUserRepository

    override val layout: Int
        get() = R.layout.fragment_calculated_requests

    lateinit var viewModel: RequestsViewModel

    private var adapter = CalculatedRequestsAdapter(onClick = { showAvailableRoutes(it) })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RequestsViewModel::class.java)

        recycler_view.adapter = adapter
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) {
                    (activity as MainActivity).hideFab(dy >= 0)
                }
            }
        })

        refresh.setOnClickListener { update() }

        viewModel.requestsFound.observe(this, EventObserver { items ->
            adapter.items = items
            hideProgress()
        })

        viewModel.noRequestsFound.observe(this, EventObserver {
            showEmptyList()
        })
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
        viewModel.getAllHandledFor(currentUserRepository.username)
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