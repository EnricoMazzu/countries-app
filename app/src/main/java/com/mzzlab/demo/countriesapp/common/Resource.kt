package com.mzzlab.demo.countriesapp.common

sealed class Resource<out T : Any> {
    data class Loading<out T: Any>(val data: T? = null, val initial:Boolean = false): Resource<T>()
    data class Success<out T : Any>(val data: T?, val fromCache: Boolean? = false) : Resource<T>()
    data class Error<out T : Any>(private val exception: Exception, val data: T? = null) : Resource<T>() {
        var hasBeenHandled = false
            private set

        fun getExceptionIfNotHandled(): Exception?{
            return if (hasBeenHandled) {
                null
            } else {
                hasBeenHandled = true
                exception
            }
        }
        fun peekException() = exception;
    }
}

fun <T: Any> isResourceLoaded(res: Resource<T>): Boolean {
    return (res is Resource.Success || res is Resource.Error)
}