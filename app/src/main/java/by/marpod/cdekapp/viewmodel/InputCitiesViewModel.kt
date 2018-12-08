package by.marpod.cdekapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.marpod.cdekapp.data.dto.City
import by.marpod.cdekapp.extensions.switchMap
import by.marpod.cdekapp.repository.CitiesRepository
import javax.inject.Inject

class InputCitiesViewModel @Inject constructor(
        private val citiesRepository: CitiesRepository
) : ViewModel() {

    private val trigger = MutableLiveData<Unit>()

    val cities: LiveData<List<City>?> = trigger.switchMap { citiesRepository.getAll() }

    fun getAll() {
        trigger.value = Unit
    }
}