package com.mzzlab.demo.countriesapp

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mzzlab.demo.countriesapp.api.DataProvider
import com.mzzlab.demo.countriesapp.common.Resource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class DataInstrumentedTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.mzzlab.demo.countriesapp", appContext.packageName)
    }

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var dataProvider: DataProvider

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @After
    fun tearDown() {
        InstrumentationRegistry.getInstrumentation().targetContext.deleteDatabase("apollo.db")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testContinentApi() = runBlocking {
        assertNotNull("DataProvider is not available",dataProvider)
        val continents = dataProvider.getContinents();
        assertNotNull("Resource is null",continents)
        assertTrue("Resource is not success",continents is Resource.Success)
        val success = continents as Resource.Success;
        assertNotNull("Data is null", success.data)
        val expectedSize = success.data?.size?:-1
        assertEquals("Unexpected number of continent",7, expectedSize )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testLanguagesApi() = runBlocking {
        assertNotNull("DataProvider is not available",dataProvider)
        val continents = dataProvider.getLanguages();
        assertNotNull("Resource is null",continents)
        assertTrue("Resource is not success",continents is Resource.Success)
        val success = continents as Resource.Success;
        assertNotNull("Data is null", success.data)
        val expectedSize = success.data?.size?:-1
        assertTrue("Unexpected number of languages", expectedSize > 20 )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testAllCountriesApi() = runBlocking {
        assertNotNull("DataProvider is not available",dataProvider)
        val continents = dataProvider.getCountries();
        assertNotNull("Resource is null",continents)
        assertTrue("Resource is not success",continents is Resource.Success)
        val success = continents as Resource.Success;
        assertNotNull("Data is null", success.data)
        val expectedSize = success.data?.size?:-1
        assertTrue("Unexpected number of languages", expectedSize > 100 )
    }

    fun testFilteredByLanguageCountriesApi() = runBlocking {
        fail("Not implemented")
    }

    fun testFilteredByContinentCountriesApi() = runBlocking {
        fail("Not implemented")
    }

    fun testFilteredByPairCountriesApi() = runBlocking {
        fail("Not implemented")
    }

    fun testFilteredNoResultCountriesApi() = runBlocking {
        fail("Not implemented")
    }
}