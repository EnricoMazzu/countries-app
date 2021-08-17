package com.mzzlab.demo.countriesapp.ui.fragment.details

import androidx.lifecycle.ViewModel
import com.mzzlab.demo.countriesapp.repo.CountriesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CountryDetailsViewModel @Inject constructor(countriesRepo: CountriesRepo): ViewModel() {

}