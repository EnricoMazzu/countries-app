package com.mzzlab.demo.countriesapp.api

open class ApiException(val code: String, msg: String): Exception(msg)