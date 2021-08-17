package com.mzzlab.demo.countriesapp.common

fun <T: Any> MutableAppData<T>.loading(data: T?) {
    value = Resource.Loading(data)
}

fun <T: Any> MutableAppData<T>.postLoading(data: T) {
    postValue(Resource.Success(data))
}

fun <T: Any> MutableAppData<T>.success(data: T) {
    value = Resource.Success(data)
}

fun <T: Any> MutableAppData<T>.postSuccess(data: T) {
    postValue(Resource.Success(data))
}

fun <T: Any> MutableAppData<T>.error(e: Exception) {
    value = Resource.Error(e)
}

fun <T: Any> MutableAppData<T>.postError(e: Exception) {
    value = Resource.Error(e)
}



