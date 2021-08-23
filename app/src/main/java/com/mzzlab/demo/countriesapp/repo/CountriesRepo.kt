package com.mzzlab.demo.countriesapp.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mzzlab.demo.countriesapp.api.DataProvider
import com.mzzlab.demo.countriesapp.common.Resource
import com.mzzlab.demo.countriesapp.model.Countries
import com.mzzlab.demo.countriesapp.model.Country
import com.mzzlab.demo.countriesapp.model.CountryDetails
import com.mzzlab.demo.countriesapp.model.CountryFilters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountriesRepo @Inject constructor(private val dataProvider: DataProvider) {

    //work as a memory cache
    private val _countries: MutableStateFlow<Resource<Countries>> by lazy {
        MutableStateFlow(Resource.Loading(initial = true));
    }
    val countries: Flow<Resource<Countries>> get()  = _countries

    private val _selectedCountry: MutableLiveData<Country?> by lazy {
        MutableLiveData()
    }
    val selectedCountry: LiveData<Country?> get() = _selectedCountry

    suspend fun reload(countryFilters: CountryFilters? = null){
        loadCountriesInternal(countryFilters = countryFilters, useNetwork = true)
    }

    suspend fun load(countryFilters: CountryFilters? = null){
        val current = _countries.value;
        //if(isToLoadFromDataSource(current)){
            loadCountriesInternal(
                countryFilters = countryFilters,
                useNetwork = false
            )
        //}
    }

    private fun isToLoadFromDataSource(current: Resource<Countries>) = current is Resource.Loading && current.initial

    private suspend fun loadCountriesInternal(countryFilters: CountryFilters? = null, useNetwork: Boolean) {
        Timber.d("loadCountriesInternal useNetwork: %s", useNetwork)
        _countries.emit(Resource.Loading())
        val resource = dataProvider.getCountries(
            filter = countryFilters,
            useNetwork = useNetwork
        );
        _countries.emit(resource)
    }

    fun setSelectedCountry(country: Country?) {
        _selectedCountry.postValue(country)
    }

    fun getCountryDetails(code:String): Flow<Resource<CountryDetails>> {
        return flow {
            emit(Resource.Loading())
            val res = dataProvider.getCountryDetails(code)
            emit(res)
        }.flowOn(Dispatchers.IO)
    }

}