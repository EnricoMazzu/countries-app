package com.mzzlab.demo.countriesapp.api.impl

import androidx.lifecycle.LiveData
import com.apollographql.apollo3.ApolloClient
import com.mzzlab.demo.countriesapp.api.DataProvider
import com.mzzlab.demo.countriesapp.common.Resource
import com.mzzlab.demo.countriesapp.model.Countries
import com.mzzlab.demo.countriesapp.model.CountryDetails

class GraphQlDataProvider(val client: ApolloClient): DataProvider {

    override fun getCountries(): LiveData<Resource<Countries>> {
        TODO("Not yet implemented")
    }

    override fun getCountryDetails(code: String): LiveData<Resource<CountryDetails>> {
        TODO("Not yet implemented")
    }

}