package com.mzzlab.demo.countriesapp.ui.fragment.countries

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mzzlab.demo.countriesapp.common.AppData
import com.mzzlab.demo.countriesapp.model.Countries
import com.mzzlab.demo.countriesapp.model.Country
import com.mzzlab.demo.countriesapp.model.CountryFilters
import com.mzzlab.demo.countriesapp.model.isNullOrEmpty
import com.mzzlab.demo.countriesapp.repo.CountriesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val countriesRepo: CountriesRepo,
    private val savedStateHandle: SavedStateHandle,
    ): ViewModel() {

    private val filters = MutableStateFlow (
        savedStateHandle.get(COUNTRY_FILTERS_KEY) ?: CountryFilters()
    )

    init {
        Timber.i(">>> Instance: %s",this)
        viewModelScope.launch {
            filters.collect {
                savedStateHandle.set(COUNTRY_FILTERS_KEY, it)
                countriesRepo.load(it)
            }
        }
    }

    fun getCountries(): AppData<Countries> {
        return countriesRepo.countries.asLiveData();
    }

    val continents = countriesRepo.getContinents().asLiveData()

    val languages = countriesRepo.getLanguages().asLiveData()


     fun reload(){
        viewModelScope.launch {
            countriesRepo.load(filters.value, true)
        }
    }

    fun selectCountry(country: Country) {
        countriesRepo.setSelectedCountry(country)
    }

    fun setFilter(filters: CountryFilters) {
        this.filters.value = filters
    }

    fun isFiltered() = this.filters.value.isNullOrEmpty()

    fun getCurrentFilter(): CountryFilters {
        return filters.value
    }

    companion object{
        const val COUNTRY_FILTERS_KEY = "COUNTRY_FILTERS_KEY"
    }
}