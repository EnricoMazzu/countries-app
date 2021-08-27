package com.mzzlab.demo.countriesapp.common

import androidx.lifecycle.LiveData

/**
 * Alias for LiveData<Resource<T>>
 */
typealias AppData<T> = LiveData<Resource<T>>


class MutableAppData<T: Any>: AppData<T>() {
    public override fun postValue(value: Resource<T>?) {
        super.postValue(value)
    }

    public override fun setValue(value: Resource<T>?) {
        super.setValue(value)
    }
}