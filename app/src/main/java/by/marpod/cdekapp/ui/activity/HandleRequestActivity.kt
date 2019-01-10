package by.marpod.cdekapp.ui.activity

import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import by.marpod.cdekapp.R
import by.marpod.cdekapp.base.BaseActivity
import by.marpod.cdekapp.data.dto.Direction
import by.marpod.cdekapp.data.dto.Request
import by.marpod.cdekapp.ui.adapter.recyclerview.DirectionsAdapter
import by.marpod.cdekapp.ui.adapter.recyclerview.HandleRequestAdapter
import by.marpod.cdekapp.util.extensions.EventObserver
import by.marpod.cdekapp.viewmodel.DirectionsViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_handle_request.*
import javax.inject.Inject

class HandleRequestActivity : BaseActivity() {

    companion object {
        const val EXTRA_REQUEST = "request"
    }

    override val layout: Int
        get() = R.layout.activity_handle_request

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val rootView: View
        get() = root

    private lateinit var directionsViewModel: DirectionsViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private lateinit var adapter: HandleRequestAdapter

    private var directions: List<Direction> = emptyList()
    private var directionsAdapter: DirectionsAdapter? = null

    private val request by lazy { intent.getParcelableExtra<Request>(EXTRA_REQUEST) }

    override fun onResume() {
        super.onResume()
        showProgress()

        directionsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(DirectionsViewModel::class.java)

        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet).apply {
            isHideable = true
        }
        hideBottomSheet()

        adapter = HandleRequestAdapter(this, request)
        recycler_view.adapter = adapter
        hideProgress()
        adapter.apply {
            onChooseDestinationClicked = { cityFrom -> openDestinationsFrom(cityFrom) }
            onAddAnotherClicked = {}
            onSaveDestinationClicked = {}
        }

        directionsViewModel.directionsFound.observe(this, EventObserver {
            directions = it
            setupDirectionsAdapter()
            hideProgress()
        })

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = "${request.cityFrom} > ${request.cityTo}"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openDestinationsFrom(cityFrom: String) {
        showProgress()
        directionsAdapter?.let {
            setupDirectionsAdapter()
        } ?: directionsViewModel.getAllFrom(cityFrom)
    }

    private fun directionClicked(direction: Direction) {
        adapter.setCard(request, direction)
        hideBottomSheet()
    }

    private fun setupDirectionsAdapter() {
        if (directionsAdapter == null) {
            directionsAdapter = DirectionsAdapter(directions, request).apply {
                onDirectionClicked = { directionClicked(it) }
            }
            directions_recycler.adapter = directionsAdapter
        } else {
            hideProgress()
        }
        showBottomSheet()
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