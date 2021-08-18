package com.mzzlab.demo.countriesapp.common

//immutable by design
sealed class Resource<out T : Any> {
    data class Success<out T : Any>(val data: T?, val fromCache: Boolean? = false) : Resource<T>()
    data class Error<out T : Any>(val exception: Exception, val data: T? = null) : Resource<T>()
    data class Loading<out T: Any>(val data: T? = null): Resource<T>()
}

fun <T: Any> isResourceLoaded(res: Resource<T>): Boolean {
    return (res is Resource.Success || res is Resource.Error)
}