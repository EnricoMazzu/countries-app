package com.mzzlab.demo.countriesapp.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mzzlab.demo.countriesapp.api.DataProvider
import com.mzzlab.demo.countriesapp.common.Resource
import com.mzzlab.demo.countriesapp.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
/**
 * The repo of countries. This class use the dataProvider instance to fetch data
 * from backend (or from cache)
 */
class CountriesRepo @Inject constructor(private val dataProvider: DataProvider) {

    private val _countries: MutableStateFlow<Resource<Countries>> by lazy {
        MutableStateFlow(Resource.Loading(initial = true));
    }
    val countries: Flow<Resource<Countries>> get()  = _countries


    suspend fun load(countryFilters: CountryFilters? = null, forceNetworkFetch: Boolean = false){
        loadCountriesInternal(
            countryFilters = countryFilters,
            useNetwork = forceNetworkFetch
        )
    }

    private suspend fun loadCountriesInternal(countryFilters: CountryFilters? = null, useNetwork: Boolean) {
        Timber.d("loadCountriesInternal useNetwork: %s", useNetwork)
        _countries.emit(Resource.Loading())
        val resource = dataProvider.getCountries(
            filter = countryFilters,
            useNetwork = useNetwork
        );
        _countries.emit(resource)
    }


    fun getCountryDetails(code:String): Flow<Resource<CountryDetails>> {
        return flow {
            emit(Resource.Loading())
            val res = dataProvider.getCountryDetails(code)
            emit(res)
        }
    }

    fun getLanguages(): Flow<Resource<List<Language>>>{
        return flow {
            emit(Resource.Loading())
            val resource = dataProvider.getLanguages();
            emit(resource)
        }
    }

    fun getContinents(): Flow<Resource<List<Continent>>>{
        return flow {
            emit(Resource.Loading())
            val resource = dataProvider.getContinents();
            emit(resource)
        }
    }

}