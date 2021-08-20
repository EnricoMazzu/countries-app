package com.mzzlab.demo.countriesapp.api

import androidx.lifecycle.LiveData
import com.mzzlab.demo.countriesapp.common.Resource
import com.mzzlab.demo.countriesapp.model.Countries
import com.mzzlab.demo.countriesapp.model.CountryDetails

interface DataProvider {
    suspend fun getCountries(): Resource<Countries>

    suspend fun getCountryDetails(code:String): Resource<CountryDetails>
}