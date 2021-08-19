package com.mzzlab.demo.countriesapp.api

enum class ErrorCode {
    GENERIC_ERROR,
    NETWORK_ERROR,
    REMOTE_SERVICE_ERROR,
    CACHE_MISS_ERROR
}

open class ApiException(val code: ErrorCode, msg: String): Exception(msg)