package com.mzzlab.demo.countriesapp.api

import com.apollographql.apollo3.exception.ApolloException
import com.mzzlab.demo.countriesapp.util.ErrorResolver

enum class ErrorCode {
    GENERIC_ERROR,
    NETWORK_ERROR,
    ENCODING_EXCEPTION,
    REMOTE_SERVICE_ERROR,
    CACHE_MISS_ERROR,
}

open class ApiException(val code: ErrorCode, message: String?, cause: Throwable? = null): Exception(message, cause)


fun Exception.asApiException(): ApiException {
    return when(this){
        is ApolloException -> ErrorResolver.resolveApolloException(this)
        is ApiException -> this
        else -> ApiException(ErrorCode.GENERIC_ERROR, this.message.orEmpty(), this)
    }
}