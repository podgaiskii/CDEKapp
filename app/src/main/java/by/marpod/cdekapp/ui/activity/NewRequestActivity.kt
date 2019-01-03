package by.marpod.cdekapp.ui.activity

import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import by.marpod.cdekapp.R
import by.marpod.cdekapp.base.BaseActivity
import by.marpod.cdekapp.data.dto.City
import by.marpod.cdekapp.data.dto.Request
import by.marpod.cdekapp.repository.CurrentUserRepository
import by.marpod.cdekapp.util.extensions.*
import by.marpod.cdekapp.viewmodel.CitiesViewModel
import by.marpod.cdekapp.viewmodel.RequestsViewModel
import kotlinx.android.synthetic.main.activity_new_request.*
import javax.inject.Inject

class NewRequestActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var currentUserRepository: CurrentUserRepository

    override val layout: Int
        get() = R.layout.activity_new_request

    private lateinit var citiesViewModel: CitiesViewModel

    private lateinit var requestsViewModel: RequestsViewModel

    override fun onResume() {
        super.onResume()
        citiesViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(CitiesViewModel::class.java)
        requestsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RequestsViewModel::class.java)

        btn_submit.setOnClickListener {
            if (allViewsValid) {
                requestsViewModel.addRequest(Request(
                        "",
                        city_from.text,
                        city_to.text,
                        length.textInt,
                        width.textInt,
                        height.textInt,
                        System.currentTimeMillis(),
                        currentUserRepository.username
                ))
            }
        }

        citiesViewModel.cities.observe(this, Observer {
            city_from.autocomplete.apply {
                setData(it)
                setOnFocusChangeListener { _, focused ->
                    if (focused) {
                        showDropDown()
                    }
                }
                setOnItemClickListener { adapterView, view, position, id ->
                    city_to.autocomplete.setData(it?.toMutableList()?.apply { removeAt(position) })
                    if (it!![position].name == city_to.text) {
                        city_to.text = ""
                    }
                }
            }
            city_to.autocomplete.apply {
                setData(it)
                setOnFocusChangeListener { _, focused ->
                    if (focused) {
                        showDropDown()
                    }
                }
            }
        })

        requestsViewModel.requestAdded.observe(this, EventObserver {
            finish()
        })

        requestsViewModel.showError.observe(this, EventObserver {
            showError(it)
        })

        for (textInputLayout in listOf(city_from, city_to, length, width, height)) {
            textInputLayout.editText!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(editText: Editable?) {
                    if (editText?.isNotBlank() == true && textInputLayout.isErrorEnabled) {
                        textInputLayout.isErrorEnabled = false
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            })
        }

        citiesViewModel.getAll()

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

    private fun AutoCompleteTextView.setData(data: List<City>?) {
        setAdapter(ArrayAdapter(context!!, android.R.layout.simple_dropdown_item_1line,
                data?.map { it.name } ?: listOf<String>()).apply {
            setNotifyOnChange(true)
        })
    }

    private val allViewsValid: Boolean
        get() = listOf(city_from, city_to, length, width, height).areValid()
}