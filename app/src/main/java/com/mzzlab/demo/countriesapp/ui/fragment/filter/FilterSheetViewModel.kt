package com.mzzlab.demo.countriesapp.ui.fragment.filter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mzzlab.demo.countriesapp.repo.CountriesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterSheetViewModel @Inject constructor(val countriesRepo: CountriesRepo): ViewModel() {
    private val _selectedCountryCode: MutableLiveData<String> by lazy{
        MutableLiveData()
    }
    private val _selectedLanguageCode: MutableLiveData<String> by lazy {
        MutableLiveData()
    }


}