package by.marpod.cdekapp.ui.fragment.user

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import by.marpod.cdekapp.R
import by.marpod.cdekapp.base.BaseFragment
import by.marpod.cdekapp.data.dto.City
import by.marpod.cdekapp.data.dto.Route
import by.marpod.cdekapp.ui.activity.MainActivity
import by.marpod.cdekapp.ui.adapter.CalculatedRequestsRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_calculated_requests.*
import javax.inject.Inject

class CalculatedRequestsFragment @Inject constructor(

) : BaseFragment() {

    override val layout: Int
        get() = R.layout.fragment_calculated_requests

    val cities = listOf(
            City("", "Minsk"),
            City("", "Vladivostok"),
            City("", "Zhdanovichi")
    )

    val routes = listOf<Route>(
//            Route(cities, 30, 300F),
//            Route(cities, 30, 300F),
//            Route(cities, 30, 300F),
//            Route(cities, 30, 300F),
//            Route(cities, 30, 300F),
//            Route(cities, 30, 300F),
//            Route(cities, 30, 300F)
    )

    private val adapter = CalculatedRequestsRecyclerViewAdapter(routes)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

        //TODO: add viewmodel observer
    }

    fun update() {
        //TODO: implement
        empty_list.isGone = adapter.itemCount == 0
    }
}