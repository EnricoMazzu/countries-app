package com.mzzlab.demo.countriesapp

import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mzzlab.demo.countriesapp.common.Resource
import com.mzzlab.demo.countriesapp.model.CountryFilters
import com.mzzlab.demo.countriesapp.repo.CountriesRepo
import com.mzzlab.demo.countriesapp.ui.fragment.countries.CountriesViewModel
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class CountriesViewModelTest {
    private lateinit var viewModel:CountriesViewModel;
    private lateinit var repo: CountriesRepo;
    private lateinit var dataProvider: MockDataProvider

    @Before
    fun setUp() {
        dataProvider = MockDataProvider();
        repo = CountriesRepo(dataProvider)
        viewModel = CountriesViewModel(repo, SavedStateHandle())
    }

    @Test
    fun testGetCountries()= runBlocking(Dispatchers.Main) {
        val lv = viewModel.getCountries();
        // check loading as first status
        val loading = lv.getOrAwaitValue()
        assertNotNull(loading)
        assertTrue(loading is Resource.Loading)
        delay(1000)
        // check become success
        val success = lv.getOrAwaitValue()
        assertNotNull(success)
        assertTrue(success is Resource.Success)
        // check content is present
        val content = (success as Resource.Success).data!!;
        assertNotNull(content)
        // check content size (mock)
        assertTrue("Invalid result size",content.size == 6)
    }

    @Test
    fun applyFilter()= runBlocking(Dispatchers.Main) {
        // emulate load initial list
        val lv = viewModel.getCountries();
        delay(1200)
        val success = lv.getOrAwaitValue()
        assertTrue(success is Resource.Success)
        val content = (success as Resource.Success).data!!;
        assertTrue("Invalid result size",content.size == 6)

        // emulate set filter
        viewModel.setFilter(CountryFilters("cname1"))
        val loading = lv.getOrAwaitValue()
        assertNotNull(loading)
        assertTrue(loading is Resource.Loading)
        delay(1000)
        // check that after x livedata become success with new result
        val final = lv.getOrAwaitValue()
        assertTrue(final is Resource.Success)
        val content2 = (final as Resource.Success).data!!;
        // check filter work
        assertEquals(3, content2.size)
    }


}