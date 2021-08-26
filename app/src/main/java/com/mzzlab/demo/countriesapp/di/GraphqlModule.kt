package com.mzzlab.demo.countriesapp.di

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.NormalizedCacheFactory
import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo3.cache.normalized.withNormalizedCache
import com.mzzlab.demo.countriesapp.BuildConfig
import com.mzzlab.demo.countriesapp.di.qualifiers.MemoryAndPersistentCache
import com.mzzlab.demo.countriesapp.di.qualifiers.MemoryOnlyCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GraphqlModule {

    @Provides
    fun provideApolloClient(@MemoryOnlyCache cache: NormalizedCacheFactory): ApolloClient {
        return with(ApolloClient(
            serverUrl = BuildConfig.COUNTRY_SERVER_URL
        )){
            if(BuildConfig.CONFIGURE_CACHE){
                this.withNormalizedCache(cache)
            }else{
                this
            }
        }
    }

    @Provides
    @MemoryAndPersistentCache
    fun providePersistentCacheFactory(@ApplicationContext context: Context): NormalizedCacheFactory {
        val sqlNormalizedCacheFactory = SqlNormalizedCacheFactory(context, BuildConfig.CACHE_DB_NAME)
        val memoryCacheFactory = MemoryCacheFactory()
        return memoryCacheFactory
            .chain(sqlNormalizedCacheFactory)
    }

    @Provides
    @MemoryOnlyCache
    fun provideMemoryCacheFactory(): NormalizedCacheFactory {
        return MemoryCacheFactory()
    }

}