package com.mzzlab.demo.countriesapp.ui.fragment.details

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mzzlab.demo.countriesapp.common.AppData
import com.mzzlab.demo.countriesapp.common.Resource
import com.mzzlab.demo.countriesapp.common.isResourceLoaded
import com.mzzlab.demo.countriesapp.model.CountryDetails
import com.mzzlab.demo.countriesapp.repo.CountriesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
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