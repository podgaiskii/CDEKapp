package by.marpod.cdekapp.ui.fragment.user

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import by.marpod.cdekapp.R
import by.marpod.cdekapp.base.BaseFragment
import by.marpod.cdekapp.data.dto.City
import by.marpod.cdekapp.extensions.autocomplete
import by.marpod.cdekapp.extensions.text
import by.marpod.cdekapp.viewmodel.InputCitiesViewModel
import kotlinx.android.synthetic.main.fragment_input_cities.*
import javax.inject.Inject

class InputCitiesFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layout: Int
        get() = R.layout.fragment_input_cities

    lateinit var viewModel: InputCitiesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(InputCitiesViewModel::class.java)

        btn_submit.setOnClickListener {
            findNavController().navigate(R.id.action_inputCitiesFragment_to_availableRoutesFragment)
        }

        viewModel.cities.observe(this, Observer {
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

        viewModel.getAll()
    }

    private fun AutoCompleteTextView.setData(data: List<City>?) {
        setAdapter(ArrayAdapter(context!!, android.R.layout.simple_dropdown_item_1line,
                data?.map { it.name } ?: listOf<String>()).apply {
            setNotifyOnChange(true)
        })
    }
}