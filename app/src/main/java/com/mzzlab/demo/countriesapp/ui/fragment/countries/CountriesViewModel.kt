package com.mzzlab.demo.countriesapp.ui.fragment.countries

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mzzlab.demo.countriesapp.common.AppData
import com.mzzlab.demo.countriesapp.model.Countries
import com.mzzlab.demo.countriesapp.model.Country
import com.mzzlab.demo.countriesapp.repo.CountriesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val countriesRepo: CountriesRepo,
    savedStateHandle: SavedStateHandle,
    ): ViewModel() {

    fun getCountries(): AppData<Countries> {
        return countriesRepo.countries.asLiveData();
    }

    init {
        viewModelScope.launch {
            countriesRepo.load()
        }
    }

    fun reload() {
        viewModelScope.launch {
            countriesRepo.reload()
        }
    }

    fun selectCountry(country: Country) {
        countriesRepo.setSelectedCountry(country)
    }
}