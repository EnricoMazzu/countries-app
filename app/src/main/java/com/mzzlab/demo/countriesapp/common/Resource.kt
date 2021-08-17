package com.mzzlab.demo.countriesapp.common

//immutable by design
sealed class Resource<out T : Any> {

    data class Success<out T : Any>(val data: T?, val fromCache: Boolean? = false) : Resource<T>()
    data class Error<out T : Any>(val exception: Exception, val data: T? = null) : Resource<T>()
    data class Loading<out T: Any>(val data: T? = null): Resource<T>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception, data=$data]"
            is Loading -> "Loading[data=$data]"
        }
    }

}

fun <T: Any> isResourceLoaded(res: Resource<T>): Boolean {
    return (res is Resource.Success || res is Resource.Error)
}