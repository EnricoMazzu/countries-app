package com.mzzlab.demo.countriesapp.common

import androidx.lifecycle.LiveData

open class AppData<T: Any>: LiveData<Resource<T>>()