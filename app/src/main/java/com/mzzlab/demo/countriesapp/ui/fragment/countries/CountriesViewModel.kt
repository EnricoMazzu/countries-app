package com.mzzlab.demo.countriesapp.ui.fragment.countries

import androidx.lifecycle.ViewModel
import com.mzzlab.demo.countriesapp.common.AppData
import com.mzzlab.demo.countriesapp.model.Countries
import com.mzzlab.demo.countriesapp.model.Country
import com.mzzlab.demo.countriesapp.repo.CountriesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(private val countriesRepo: CountriesRepo): ViewModel() {

    fun getCountries(): AppData<Countries> {
        return countriesRepo.countries;
    }

    fun reload() {
        countriesRepo.reload()
    }

    fun selectCountry(country: Country) {
        countriesRepo.setSelectedCountry(country)
    }
}