package com.mzzlab.demo.countriesapp.api

import com.mzzlab.demo.countriesapp.common.Resource
import com.mzzlab.demo.countriesapp.model.*

interface DataProvider {
    /**
     * Get all countries
     * @param filter filter parameter (nullable)
     * @param useNetwork true to try online before than cache
     */
    suspend fun getCountries(filter: CountryFilters? = null, useNetwork: Boolean? = false): Resource<Countries>

    /**
     * Get details of a specific country (by code)
     * @param code the country code (id)
     */
    suspend fun getCountryDetails(code:String): Resource<CountryDetails>

    /**
     * Get list of all continent
     */
    suspend fun getContinents(): Resource<List<Continent>>

    /**
     * Get the list of all languages
     */
    suspend fun getLanguages(): Resource<List<Language>>
}