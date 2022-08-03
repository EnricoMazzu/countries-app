package com.mzzlab.demo.countriesapp.model



data class CountryDetails(val code:String,
                          val name: String,
                          val emoji: String,
                          val phone: String,
                          val continent: String,
                          val capital: String?,
                          val currency: String?,
                          val languages: List<String>)
