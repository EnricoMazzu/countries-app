package com.mzzlab.demo.countriesapp.ui.fragment.details

import androidx.lifecycle.*
import com.mzzlab.demo.countriesapp.common.AppData
import com.mzzlab.demo.countriesapp.common.MutableAppData
import com.mzzlab.demo.countriesapp.common.Resource
import com.mzzlab.demo.countriesapp.common.isResourceLoaded
import com.mzzlab.demo.countriesapp.model.CountryDetails
import com.mzzlab.demo.countriesapp.repo.CountriesRepo
import com.mzzlab.demo.countriesapp.ui.fragment.countries.CountriesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CountryDetailsViewModel @Inject constructor(
    private val countriesRepo: CountriesRepo,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val countryDetails: MediatorLiveData<Resource<CountryDetails>> by lazy {
        MediatorLiveData()
    }

    private var countryCode:String? = null

    private fun loadDetails(code: String) {
        val source = countriesRepo.getCountryDetails(code).asLiveData()
        countryDetails.addSource(source){
            countryDetails.value = it
            if(isResourceLoaded(it)){
                countryDetails.removeSource(source)
            }
        }
    }

    fun reload(){
        loadDetails(countryCode!!)
    }

    fun getCountryDetails(): AppData<CountryDetails> = countryDetails

    fun setCountryCode(countryCode: String?) {
        this.countryCode = countryCode?: savedStateHandle.get("selected")
        reload()
    }

}