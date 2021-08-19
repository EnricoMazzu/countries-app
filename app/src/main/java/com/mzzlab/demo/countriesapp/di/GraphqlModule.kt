package com.mzzlab.demo.countriesapp.di

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo3.cache.normalized.withNormalizedCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GraphqlModule {

    @Provides
    fun provideApolloClient(@ApplicationContext context: Context): ApolloClient {
        val sqlNormalizedCacheFactory = SqlNormalizedCacheFactory(context, "apollo.db")
        return ApolloClient(
            serverUrl = "https://countries.trevorblades.com"
        ).withNormalizedCache(sqlNormalizedCacheFactory);
    }

}