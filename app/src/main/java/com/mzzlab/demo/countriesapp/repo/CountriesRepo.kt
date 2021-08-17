package com.mzzlab.demo.countriesapp.repo

import com.mzzlab.demo.countriesapp.api.DataProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountriesRepo @Inject constructor(val dataProvider: DataProvider){

}