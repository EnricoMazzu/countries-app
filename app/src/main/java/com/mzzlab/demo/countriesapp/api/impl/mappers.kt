package com.mzzlab.demo.countriesapp.api.impl

import com.mzzlab.demo.countriesapp.graphql.*
import com.mzzlab.demo.countriesapp.model.Continent
import com.mzzlab.demo.countriesapp.model.Country
import com.mzzlab.demo.countriesapp.model.CountryDetails
import com.mzzlab.demo.countriesapp.model.Language

fun CountriesQuery.Data?.mapToModel(): List<Country> {
    return this?.let {
        it.countries.map { c ->
            Country(
                code = c.code,
                name = c.name,
                emoji = c.emoji,
                languages = c.languages.map {  l-> Language(
                    code = l.code,
                    name = l.name.orEmpty()
                )}
            )
        }
    }?: ArrayList()
}


fun FilteredCountriesQuery.Data?.mapToModel(): List<Country> {
    return this?.let {
        it.countries.map { c ->
            Country(
                code = c.code,
                name = c.name,
                emoji = c.emoji,
                languages = c.languages.map {  l-> Language(
                    code = l.code,
                    name = l.name.orEmpty()
                )}
            )
        }
    }?: ArrayList()
}


fun CountryDetailsQuery.Data?.mapToModel(): CountryDetails? {
    return this?.country?.let {
        CountryDetails(
            code = it.code,
            name = it.name,
            phone = it.phone,
            emoji = it.emoji,
            capital = it.capital,
            continent = it.continent.name,
            currency = it.currency,
            languages = it.languages.map { l -> l.name.orEmpty() })
    }
}

fun ContinentsQuery.Data?.mapToModel(): List<Continent>{
    return this?.continents?.map {
        Continent(
            code = it.code,
            name = it.name
        )
    }?: ArrayList()
}

fun LanguagesQuery.Data?.mapToModel(): List<Language>{
    return this?.languages?.map {
        Language(
            code = it.code,
            name = it.name.orEmpty()
        )
    }?: ArrayList()
}