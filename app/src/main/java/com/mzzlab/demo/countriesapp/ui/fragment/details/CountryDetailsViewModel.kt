package com.mzzlab.demo.countriesapp.ui.fragment.details

import androidx.lifecycle.ViewModel
import com.mzzlab.demo.countriesapp.common.AppData
import com.mzzlab.demo.countriesapp.model.CountryDetails
import com.mzzlab.demo.countriesapp.repo.CountriesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CountryDetailsViewModel @Inject constructor(private val countriesRepo: CountriesRepo): ViewModel() {

    fun getCountryDetails(): AppData<CountryDetails> {
        val code = countriesRepo.selectedCountry.value?.code;
        return countriesRepo.getCountryDetails(code!!)
    }
}