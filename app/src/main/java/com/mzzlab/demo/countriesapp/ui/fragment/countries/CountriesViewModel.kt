package com.mzzlab.demo.countriesapp.ui.fragment.countries

import androidx.lifecycle.*
import com.mzzlab.demo.countriesapp.common.AppData
import com.mzzlab.demo.countriesapp.model.*
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

    private val filters: MutableStateFlow<CountryFilters> by lazy {
        val initialValue = savedStateHandle.get(COUNTRY_FILTERS_KEY) ?: CountryFilters()
        MutableStateFlow(initialValue)
    }

    init {
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

    fun getContinents(): AppData<List<Continent>> {
        return countriesRepo.getContinents().asLiveData()
    }

    fun getLanguages(): AppData<List<Language>> {
        return countriesRepo.getLanguages().asLiveData()
    }

     fun reload(){
        viewModelScope.launch {
            countriesRepo.load(filters.value, true)
        }
    }

    fun setFilter(filters: CountryFilters) {
        this.filters.value = filters
    }

    fun getCurrentFilter(): CountryFilters {
        return filters.value
    }

    companion object{
        const val COUNTRY_FILTERS_KEY = "COUNTRY_FILTERS_KEY"
    }
}