package by.marpod.cdekapp.ui.fragment.user

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import by.marpod.cdekapp.R
import by.marpod.cdekapp.base.BaseFragment
import by.marpod.cdekapp.repository.CurrentUserRepository
import by.marpod.cdekapp.ui.activity.MainActivity
import by.marpod.cdekapp.ui.adapter.SentRequestsRecyclerViewAdapter
import by.marpod.cdekapp.util.extensions.EventObserver
import by.marpod.cdekapp.viewmodel.RequestsViewModel
import kotlinx.android.synthetic.main.fragment_sent_requests.*
import javax.inject.Inject

class SentRequestsFragment @Inject constructor() : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var currentUserRepository: CurrentUserRepository

    override val layout: Int
        get() = R.layout.fragment_sent_requests

    lateinit var viewModel: RequestsViewModel

    private var adapter = SentRequestsRecyclerViewAdapter()

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
        update()
        swipe_refresh.setOnRefreshListener { update() }

        viewModel.requestsFound.observe(this, EventObserver {
            adapter.items = it ?: emptyList()
        })
    }

    private fun update() {
        viewModel.getAllFor(currentUserRepository.username)
    }
}