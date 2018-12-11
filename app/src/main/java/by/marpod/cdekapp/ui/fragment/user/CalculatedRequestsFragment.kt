package by.marpod.cdekapp.ui.fragment.user

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.marpod.cdekapp.R
import by.marpod.cdekapp.base.BaseFragment
import by.marpod.cdekapp.data.dto.City
import by.marpod.cdekapp.data.dto.Route
import by.marpod.cdekapp.ui.activity.MainActivity
import by.marpod.cdekapp.ui.adapter.AvailableRoutesRecyclerViewAdapter
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

    val routes = listOf(
            Route(cities, 30, 300F),
            Route(cities, 30, 300F),
            Route(cities, 30, 300F),
            Route(cities, 30, 300F),
            Route(cities, 30, 300F),
            Route(cities, 30, 300F),
            Route(cities, 30, 300F)
    )

    val adapter = AvailableRoutesRecyclerViewAdapter(routes)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rv_routes.adapter = adapter
        rv_routes.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) {
                    (activity as MainActivity).hideFab(dy >= 0)
                }
            }
        })
    }
}