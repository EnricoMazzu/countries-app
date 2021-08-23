package com.mzzlab.demo.countriesapp.api

import com.mzzlab.demo.countriesapp.common.Resource
import com.mzzlab.demo.countriesapp.model.*

interface DataProvider {

    suspend fun getCountries(filter: CountryFilters? = null, useNetwork: Boolean? = false): Resource<Countries>

    suspend fun getCountryDetails(code:String): Resource<CountryDetails>

    suspend fun getContinents(): Resource<List<Continent>>

    suspend fun getLanguages(): Resource<List<Language>>
}