package com.mzzlab.demo.countriesapp.api.impl

import androidx.lifecycle.liveData
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.cache.normalized.isFromCache
import com.apollographql.apollo3.cache.normalized.watch
import com.mzzlab.demo.countriesapp.api.ApiException
import com.mzzlab.demo.countriesapp.api.DataProvider
import com.mzzlab.demo.countriesapp.common.AppData
import com.mzzlab.demo.countriesapp.common.Resource
import com.mzzlab.demo.countriesapp.graphql.CountriesQuery
import com.mzzlab.demo.countriesapp.graphql.CountryDetailsQuery
import com.mzzlab.demo.countriesapp.model.Countries
import com.mzzlab.demo.countriesapp.model.Country
import com.mzzlab.demo.countriesapp.model.CountryDetails

class GraphQlDataProvider(private val client: ApolloClient): DataProvider {

    override fun getCountries(): AppData<Countries> {
        return liveData {
            emit(Resource.Loading())
            // according to doc, apollo is main thread safe: execute query in background
            // on Dispatcher.IO
            val resultResource = getCountriesResource();
            // emit result (Success or Error)
            emit(resultResource)
        }
    }

    private suspend fun getCountriesResource(): Resource<Countries>{
        return try {
            val result = client.query(CountriesQuery())
            when(result.hasErrors()){
                false -> Resource.Success(mapToModel(result.data), result.isFromCache)
                else -> Resource.Error(createError(result.errors!!))
            }
        }catch (ex:Exception){
            Resource.Error(ex)
        }
    }

    private fun mapToModel(data: CountriesQuery.Data?): List<Country> {
        return data?.let {
            it.countries.map { c ->
                Country(
                    code = c.code,
                    name = c.name,
                    emoji = c.emoji)
            }
        }?: ArrayList()
    }

    private fun mapToModel(data: CountryDetailsQuery.Data?): CountryDetails? {
        return data?.country?.let {
            CountryDetails(
                code = it.code,
                name = it.name,
                phone = it.phone,
                emoji = it.emoji,
                capital = it.capital,
                continent = it.continent.name,
                currency = it.currency,
                languages = it.languages.map { l -> l.name.orEmpty()})
        }
    }


    override fun getCountryDetails(code: String): AppData<CountryDetails> {
        return liveData {
            emit(Resource.Loading())
            // according to doc, apollo is main thread safe: execute query in background
            // on Dispatcher.IO
            val resultResource = getCountryDetailsResource(code);
            // emit result (Success or Error)
            emit(resultResource)
        }
    }


    suspend fun getCountryDetailsResource(code: String): Resource<CountryDetails>{
        return try {
            val result = client.query(CountryDetailsQuery(code))
            result.data
            when(result.hasErrors()){
                false -> Resource.Success(mapToModel(result.data), result.isFromCache)
                else -> Resource.Error(createError(result.errors!!))
            }
        }catch (ex:Exception){
            Resource.Error(ex)
        }
    }

    private fun createError(errors: List<Error>): Exception {
        //TODO complete this
        return ApiException("TODO", "to be completed");
    }

}