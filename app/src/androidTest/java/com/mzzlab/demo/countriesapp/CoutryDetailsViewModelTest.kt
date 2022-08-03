package com.mzzlab.demo.countriesapp

import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mzzlab.demo.countriesapp.common.Resource
import com.mzzlab.demo.countriesapp.model.CountryDetails
import com.mzzlab.demo.countriesapp.repo.CountriesRepo
import com.mzzlab.demo.countriesapp.ui.fragment.countries.CountriesViewModel
import com.mzzlab.demo.countriesapp.ui.fragment.details.CountryDetailsViewModel
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class CountryDetailsViewModelTest {
    private lateinit var viewModel: CountryDetailsViewModel;
    private lateinit var repo: CountriesRepo;
    private lateinit var dataProvider: MockDataProvider

    @Before
    fun setUp() {
        dataProvider = MockDataProvider();
        repo = CountriesRepo(dataProvider)
        viewModel = CountryDetailsViewModel(repo, SavedStateHandle())
    }

    @Test
    fun testLoadDetails() = runBlocking(Dispatchers.Main){
        viewModel.setCountryCode("cd1")
        val lv = viewModel.getCountryDetails();
        assertTrue(lv.getOrAwaitValue() is Resource.Loading)
        delay(1200)
        val successResource = lv.getOrAwaitValue();
        assertTrue(successResource is Resource.Success)
        val data = (successResource as Resource.Success).data!!
        assertNotNull("data is null", data)
        assertEquals("unexpected code","cd1",data.code)
        assertEquals("unexpected name","cdname1",data.name)
        assertEquals("unexpected emoji","em1",data.emoji)
        assertEquals("unexpected phone","+34",data.phone)
        assertEquals("unexpected continent","cname1",data.continent)
        assertEquals("unexpected currency","EUR",data.currency)
        assertEquals("unexpected capital","cdc1",data.capital)
        assertNotNull("unexpected null languages", data.languages)
        assertTrue("invalid languages", data.languages.size == 1 && data.languages[0] == "l1")
    }

    @Test
    fun testReLoadDetails() = runBlocking(Dispatchers.Main){
        viewModel.setCountryCode("cd1")
        val lv = viewModel.getCountryDetails();
        lv.getOrAwaitValue();
        delay(1200)
        val successResource = lv.getOrAwaitValue();
        assertTrue(successResource is Resource.Success)
        viewModel.reload()
        delay(1200)
        val data = (successResource as Resource.Success).data!!
        assertNotNull("data is null", data)
        assertEquals("unexpected code","cd1",data.code)
        assertEquals("unexpected name","cdname1",data.name)
        assertEquals("unexpected emoji","em1",data.emoji)
        assertEquals("unexpected phone","+34",data.phone)
        assertEquals("unexpected continent","cname1",data.continent)
        assertEquals("unexpected currency","EUR",data.currency)
        assertEquals("unexpected capital","cdc1",data.capital)
        assertNotNull("unexpected null languages", data.languages)
        assertTrue("invalid languages", data.languages.size == 1 && data.languages[0] == "l1")
    }
}