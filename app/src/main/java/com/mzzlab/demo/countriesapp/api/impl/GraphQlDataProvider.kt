package com.mzzlab.demo.countriesapp.api.impl

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloRequest
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.api.Query
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.isFromCache
import com.apollographql.apollo3.cache.normalized.withFetchPolicy
import com.mzzlab.demo.countriesapp.api.ApiException
import com.mzzlab.demo.countriesapp.api.DataProvider
import com.mzzlab.demo.countriesapp.api.ErrorCode
import com.mzzlab.demo.countriesapp.common.Resource
import com.mzzlab.demo.countriesapp.graphql.*
import com.mzzlab.demo.countriesapp.model.*

class GraphQlDataProvider(private val client: ApolloClient) : DataProvider {

    override suspend fun getCountries(
        filter: CountryFilters?,
        useNetwork: Boolean?
    ): Resource<Countries> {
        val query = filter?.continent?.let {
            FilteredCountriesQuery(it) // receive filtered from backend
        }?: CountriesQuery()
        val request = createCountriesApolloRequest(query, useNetwork)
        val originalResource:Resource<Countries> =  fetchCountries(request){
            when(it){
                is FilteredCountriesQuery.Data -> it.mapToModel();
                is CountriesQuery.Data -> it.mapToModel();
                else -> throw IllegalArgumentException("Invalid Query Data type")
            }
        }
        return applyClientSideFilter(originalResource, filter?.language);
    }

    private fun applyClientSideFilter(resource: Resource<Countries>, language: String?): Resource<Countries> {
        if(resource is Resource.Success && !language.isNullOrEmpty()){
            return Resource.Success(
                resource.data?.filter{
                    it.languages.contains(language)
                }?: ArrayList()
                , resource.fromCache)
        }
        return resource;
    }

    private suspend fun <D : Query.Data> fetchCountries(
        request: ApolloRequest<D>,
        mapper: (D?) -> Countries
    ): Resource<List<Country>> {
        val result = client.query(request)
        return when (result.hasErrors()) {
            false -> Resource.Success(mapper.invoke(result.data), result.isFromCache)
            else -> Resource.Error(createError(result, result.errors!!))
        }
    }

    private fun <D : Query.Data> createCountriesApolloRequest(
        query: Query<D>,
        useNetwork: Boolean?
    ): ApolloRequest<D> {
        return ApolloRequest(query)
            .withFetchPolicy(
                if (useNetwork == true) {
                    FetchPolicy.NetworkFirst
                } else {
                    FetchPolicy.CacheFirst
                }
            )
    }

    override suspend fun getCountryDetails(code: String): Resource<CountryDetails> {
        return getCountryDetailsResource(code)
    }

    override suspend fun getContinents(): Resource<List<Continent>> {
        return getContinentsResource();
    }

    private suspend fun getContinentsResource(): Resource<List<Continent>> {
        return try {
            val result = client.query(ContinentsQuery())
            result.data
            when (result.hasErrors()) {
                false -> Resource.Success(result.data.mapToModel(), result.isFromCache)
                else -> Resource.Error(createError(result, result.errors!!))
            }
        } catch (ex: Exception) {
            Resource.Error(ex)
        }
    }

    override suspend fun getLanguages(): Resource<List<Language>> {
        return getLanguagesResource();
    }

    private suspend fun getLanguagesResource(): Resource<List<Language>> {
        return try {
            val result = client.query(LanguagesQuery())
            result.data
            when (result.hasErrors()) {
                false -> Resource.Success(result.data.mapToModel(), result.isFromCache)
                else -> Resource.Error(createError(result, result.errors!!))
            }
        } catch (ex: Exception) {
            Resource.Error(ex)
        }
    }

    private suspend fun getCountryDetailsResource(code: String): Resource<CountryDetails> {
        return try {
            val result = client.query(CountryDetailsQuery(code))
            result.data
            when (result.hasErrors()) {
                false -> Resource.Success(result.data.mapToModel(), result.isFromCache)
                else -> Resource.Error(createError(result, result.errors!!))
            }
        } catch (ex: Exception) {
            Resource.Error(ex)
        }
    }

    private fun <D: Query.Data> createError(result: ApolloResponse<D>, errors: List<Error>): Exception {
        return ApiException(ErrorCode.GENERIC_ERROR, "to be completed");
    }

}