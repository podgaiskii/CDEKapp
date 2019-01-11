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
import by.marpod.cdekapp.data.CalculatedRequest
import by.marpod.cdekapp.data.Direction
import by.marpod.cdekapp.data.Request
import by.marpod.cdekapp.ui.adapter.recyclerview.DirectionsAdapter
import by.marpod.cdekapp.ui.adapter.recyclerview.HandleRequestAdapter
import by.marpod.cdekapp.util.extensions.EventObserver
import by.marpod.cdekapp.viewmodel.CalculatedRequestsViewModel
import by.marpod.cdekapp.viewmodel.DirectionsViewModel
import by.marpod.cdekapp.viewmodel.RequestsViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_handle_request.*
import javax.inject.Inject

class HandleRequestActivity : BaseActivity() {

    companion object {
        const val EXTRA_REQUEST = "request"
        const val EXTRA_INDEX = "currentIndex"
    }

    override val layout: Int
        get() = R.layout.activity_handle_request

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val rootView: View
        get() = root

    private lateinit var directionsViewModel: DirectionsViewModel
    private lateinit var calculatedRequestsViewModel: CalculatedRequestsViewModel
    private lateinit var requestsViewModel: RequestsViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private lateinit var adapter: HandleRequestAdapter

    private var isUpdateDirectionsNeeded: Boolean = true

    private var directions: List<Direction> = emptyList()
    private var directionsAdapter: DirectionsAdapter? = null

    private val request by lazy { intent.getParcelableExtra<Request>(EXTRA_REQUEST) }
    private var currentIndex: Int = 0

    override fun onResume() {
        super.onResume()
        showProgress()

        currentIndex = intent.getIntExtra(EXTRA_INDEX, 0)

        directionsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(DirectionsViewModel::class.java)
        calculatedRequestsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(CalculatedRequestsViewModel::class.java)
        requestsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RequestsViewModel::class.java)

        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet).apply {
            isHideable = true
        }
        hideBottomSheet()

        setupAdapter()
        hideProgress()
        adapter.apply {
            onChooseDirectionClicked = { cityFrom -> openDestinationsFrom(cityFrom) }
            onAddAnotherClicked = { addRequest() }
            onSaveRequestClicked = { saveRequest(it) }
            onSaveDirectionClicked = { addDirection(it) }
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
        if (!isUpdateDirectionsNeeded) {
            setupDirectionsAdapter()
        } else {
            directionsViewModel.getAllFrom(cityFrom)
        }
    }

    private fun addRequest() {
        currentIndex++
        directions = emptyList()
        isUpdateDirectionsNeeded = true
        setupAdapter()
        setupDirectionsAdapter()
    }

    private fun saveRequest(directions: List<Direction>) {
        val calculatedRequest = CalculatedRequest(request.id, currentIndex, request, mapOf(currentIndex to directions))
        calculatedRequestsViewModel.add(currentIndex, calculatedRequest)
        requestsViewModel.setHandled(request)
        finish()
    }

    private fun addDirection(direction: Direction) {
        adapter.directions = adapter.directions + direction
        isUpdateDirectionsNeeded = true
    }

    private fun directionClicked(direction: Direction) {
        adapter.setCard(request, direction)
        hideBottomSheet()
    }

    private fun setupAdapter() {
        adapter = HandleRequestAdapter(request)
        recycler_view.adapter = adapter
    }

    private fun setupDirectionsAdapter() {
        if (directionsAdapter == null || isUpdateDirectionsNeeded) {
            directionsAdapter = DirectionsAdapter(directions, request).apply {
                onDirectionClicked = { directionClicked(it) }
            }
            directions_recycler.adapter = directionsAdapter
            isUpdateDirectionsNeeded = false
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