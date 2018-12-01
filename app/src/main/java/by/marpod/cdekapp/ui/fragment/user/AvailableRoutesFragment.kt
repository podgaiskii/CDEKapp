package by.marpod.cdekapp.ui.fragment.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.marpod.cdekapp.R
import by.marpod.cdekapp.base.BaseFragment
import by.marpod.cdekapp.databinding.FragmentAvailableRoutesBinding
import by.marpod.cdekapp.dto.City
import by.marpod.cdekapp.dto.Route
import by.marpod.cdekapp.ui.adapter.AvailableRoutesRecyclerViewAdapter
import by.marpod.cdekapp.util.autoCleared

class AvailableRoutesFragment : BaseFragment() {

    override val layout: Int
        get() = R.layout.fragment_available_routes

    var binding by autoCleared<FragmentAvailableRoutesBinding>()

    val cities = listOf(
            City("Minsk"),
            City("Vladivostok"),
            City("Zhdanovichi")
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAvailableRoutesBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@AvailableRoutesFragment)

            rvRoutes.adapter = adapter
        }

        return binding.root
    }
}