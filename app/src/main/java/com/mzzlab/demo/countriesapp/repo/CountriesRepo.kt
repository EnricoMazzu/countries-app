package com.mzzlab.demo.countriesapp.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mzzlab.demo.countriesapp.api.DataProvider
import com.mzzlab.demo.countriesapp.common.AppData
import com.mzzlab.demo.countriesapp.common.MutableAppData
import com.mzzlab.demo.countriesapp.common.Resource
import com.mzzlab.demo.countriesapp.common.isResourceLoaded
import com.mzzlab.demo.countriesapp.model.Countries
import com.mzzlab.demo.countriesapp.model.Country
import com.mzzlab.demo.countriesapp.model.CountryDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountriesRepo @Inject constructor(private val dataProvider: DataProvider) {

    private val _countries: MediatorLiveData<Resource<Countries>> by lazy {
        val lv = MediatorLiveData<Resource<Countries>>()
        loadCountriesInternal(lv)
        lv
    }
    val countries: AppData<Countries> get() = _countries

    private val _selectedCountry: MutableLiveData<Country?> by lazy {
        MutableLiveData()
    }
    val selectedCountry: LiveData<Country?> get() = _selectedCountry

    private fun loadCountriesInternal(mediator: MediatorLiveData<Resource<Countries>>){
        val source = getCountriesSource();
        mediator.addSource(source) {
            // detach the source when resource is loaded
            if(isResourceLoaded(it)){
                mediator.removeSource(source)
            }
            mediator.postValue(it);
        }
    }

    private fun getCountriesSource(): AppData<Countries> {
        return dataProvider.getCountries();
    }

    fun reload(){
        loadCountriesInternal(_countries)
    }

    fun setSelectedCountry(country: Country?) {
        _selectedCountry.postValue(country)
    }

    fun getCountryDetails(code:String): AppData<CountryDetails> {
        return dataProvider.getCountryDetails(code)
    }

}