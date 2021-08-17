package com.mzzlab.demo.countriesapp.api

import androidx.lifecycle.LiveData
import com.mzzlab.demo.countriesapp.common.Resource
import com.mzzlab.demo.countriesapp.model.Countries
import com.mzzlab.demo.countriesapp.model.CountryDetails

interface DataProvider {
    fun getCountries(): LiveData<Resource<Countries>>

    fun getCountryDetails(code:String): LiveData<Resource<CountryDetails>>
}