package com.mzzlab.demo.countriesapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryFilters(val continent: String? = null, val language:String? = null): Parcelable{
    fun isEmpty() = continent.isNullOrEmpty() && language.isNullOrEmpty()
}


fun CountryFilters?.isNullOrEmpty(): Boolean = this?.isEmpty() ?: true
