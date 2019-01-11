package by.marpod.cdekapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import by.marpod.cdekapp.R
import by.marpod.cdekapp.base.BaseFragment
import by.marpod.cdekapp.data.Request
import by.marpod.cdekapp.ui.adapter.recyclerview.HandleRequestAdapter
import by.marpod.cdekapp.util.extensions.EventObserver
import by.marpod.cdekapp.viewmodel.DirectionsViewModel
import kotlinx.android.synthetic.main.fragment_calculated_request.*
import javax.inject.Inject

class CalculatedRequestFragment : BaseFragment() {

    companion object {
        const val ARG_REQUEST = "request"
        const val ARG_INDEX = "index"
        const val ARG_DIRECTIONS = "directions"

        fun newInstance(request: Request, index: Int, directions: HashMap<Int, List<String>>) = CalculatedRequestFragment().apply {
            arguments = bundleOf(
                    ARG_REQUEST to request,
                    ARG_INDEX to index,
                    ARG_DIRECTIONS to directions
            )
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layout: Int
        get() = R.layout.fragment_calculated_request

    lateinit var viewModel: DirectionsViewModel

    private val request by lazy { arguments?.getParcelable<Request>(ARG_REQUEST)!! }
    private val index by lazy { arguments?.getInt(ARG_INDEX, 0) }
    private val directionsIds by lazy { arguments?.getSerializable(ARG_DIRECTIONS) as HashMap<Int, List<String>> }

    private val adapter by lazy { HandleRequestAdapter(request) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(DirectionsViewModel::class.java)

        viewModel.getAll(directionsIds[index]!!)
        showProgress()

        viewModel.directionsFound.observe(this, EventObserver {
            recycler_view.adapter = adapter.apply {
                directions = it
            }
            hideProgress()
        })
    }

    private fun showProgress() {
        progress.isVisible = true
    }

    private fun hideProgress() {
        progress.isGone = true
    }
}