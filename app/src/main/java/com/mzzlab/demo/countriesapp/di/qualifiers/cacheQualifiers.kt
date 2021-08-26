package com.mzzlab.demo.countriesapp.di.qualifiers

import javax.inject.Qualifier

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class MemoryAndPersistentCache()


@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class MemoryOnlyCache()
