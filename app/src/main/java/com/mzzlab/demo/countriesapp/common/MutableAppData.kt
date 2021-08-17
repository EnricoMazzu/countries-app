package com.mzzlab.demo.countriesapp.common

class MutableAppData<T: Any>: AppData<T>() {
    public override fun postValue(value: Resource<T>?) {
        super.postValue(value)
    }

    public override fun setValue(value: Resource<T>?) {
        super.setValue(value)
    }
}