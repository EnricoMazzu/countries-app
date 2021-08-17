package com.mzzlab.demo.countriesapp.di

import com.apollographql.apollo3.ApolloClient
import com.mzzlab.demo.countriesapp.api.DataProvider
import com.mzzlab.demo.countriesapp.api.impl.GraphQlDataProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataProvidersModule {

    @Provides
    @Singleton
    fun provideDataProvider(client: ApolloClient):DataProvider {
        return GraphQlDataProvider(client)
    }
}