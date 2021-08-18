package com.mzzlab.demo.countriesapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mzzlab.demo.countriesapp.model.Country
import com.mzzlab.demo.countriesapp.repo.CountriesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val countriesRepo: CountriesRepo): ViewModel() {


    val selectedCountry: LiveData<Country?> get() = countriesRepo.selectedCountry

    fun invalidateSelection() {
        countriesRepo.setSelectedCountry(null)
    }
}