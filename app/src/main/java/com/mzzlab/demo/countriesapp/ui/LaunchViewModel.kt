package com.mzzlab.demo.countriesapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mzzlab.demo.countriesapp.common.Resource
import com.mzzlab.demo.countriesapp.model.Continent
import com.mzzlab.demo.countriesapp.model.Language
import com.mzzlab.demo.countriesapp.repo.CountriesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(val countriesRepo: CountriesRepo): ViewModel() {

    

}
