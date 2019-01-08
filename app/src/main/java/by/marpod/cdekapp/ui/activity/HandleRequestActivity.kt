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
import by.marpod.cdekapp.data.dto.Request
import by.marpod.cdekapp.ui.adapter.HandleRequestRecyclerViewAdapter
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
    private lateinit var adapter: HandleRequestRecyclerViewAdapter

    private val request by lazy { intent.getParcelableExtra<Request>(EXTRA_REQUEST) }

    override fun onResume() {
        super.onResume()
        showProgress()

        directionsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(DirectionsViewModel::class.java)

//        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
//        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        bottom_sheet.isGone = true

        adapter = HandleRequestRecyclerViewAdapter(this, request)
        recycler_view.adapter = adapter
        hideProgress()
        adapter.apply {
            onChooseDestinationClicked = { cityFrom -> openDestinationsFrom(cityFrom) }
            onAddAnotherClicked = {}
            onSaveDestinationClicked = {}
        }

        directionsViewModel.directionsFound.observe(this, EventObserver {

            hideProgress()
        })

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)

        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openDestinationsFrom(cityFrom: String) {
        //TODO: add bottom sheet dialog with destinations
    }

    private fun showProgress() {
        progress.isVisible = true
    }

    private fun hideProgress() {
        progress.isGone = true
    }

    fun showBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}