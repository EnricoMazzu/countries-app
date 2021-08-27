package com.mzzlab.demo.countriesapp.util

import com.apollographql.apollo3.exception.*
import com.mzzlab.demo.countriesapp.api.ApiException
import com.mzzlab.demo.countriesapp.api.ErrorCode

object ErrorResolver {

    @JvmStatic
    val mapper = mapOf(
        ApolloNetworkException::class to ErrorCode.NETWORK_ERROR,
        ApolloWebSocketClosedException::class to ErrorCode.NETWORK_ERROR,
        ApolloHttpException::class to ErrorCode.NETWORK_ERROR,
        JsonEncodingException::class to ErrorCode.ENCODING_EXCEPTION,
        JsonDataException::class to ErrorCode.ENCODING_EXCEPTION,
        ApolloParseException::class to ErrorCode.ENCODING_EXCEPTION,
        CacheMissException::class to ErrorCode.NETWORK_ERROR,
        HttpCacheMissException::class to ErrorCode.NETWORK_ERROR,
        ApolloCompositeException::class to ErrorCode.NETWORK_ERROR,
        AutoPersistedQueriesNotSupported::class to ErrorCode.NETWORK_ERROR,
        MissingValueException::class to ErrorCode.NETWORK_ERROR,
    )

    fun resolveApolloException(ex: ApolloException): ApiException{
        val code:ErrorCode? = if(ex is ApolloCompositeException){
            mapper[ex.second::class]
        }else{
            mapper[ex::class]
        }
        return code?.let {
            ApiException(code, ex.message, ex)
        }?: ApiException(ErrorCode.GENERIC_ERROR, ex.message, ex)
    }
}
