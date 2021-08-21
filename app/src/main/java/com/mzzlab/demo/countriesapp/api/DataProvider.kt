package com.mzzlab.demo.countriesapp.api

import com.mzzlab.demo.countriesapp.common.Resource
import com.mzzlab.demo.countriesapp.model.Countries
import com.mzzlab.demo.countriesapp.model.CountryDetails
import com.mzzlab.demo.countriesapp.model.CountryFilters

interface DataProvider {

    suspend fun getCountries(filter: CountryFilters? = null, useNetwork: Boolean? = false): Resource<Countries>

    suspend fun getCountryDetails(code:String): Resource<CountryDetails>

}