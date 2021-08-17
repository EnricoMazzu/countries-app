package com.mzzlab.demo.countriesapp.repo

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.mzzlab.demo.countriesapp.api.DataProvider
import com.mzzlab.demo.countriesapp.common.AppData
import com.mzzlab.demo.countriesapp.common.Resource
import com.mzzlab.demo.countriesapp.common.isResourceLoaded
import com.mzzlab.demo.countriesapp.model.Countries
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

    fun reload(){

        loadCountriesInternal(_countries)
    }

    private fun loadCountriesInternal(mediator: MediatorLiveData<Resource<Countries>>){
        mediator.value = Resource.Loading()
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


}