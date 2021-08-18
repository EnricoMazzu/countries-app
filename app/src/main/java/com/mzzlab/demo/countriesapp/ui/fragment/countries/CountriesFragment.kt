package com.mzzlab.demo.countriesapp.ui.fragment.countries

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mzzlab.demo.countriesapp.R
import com.mzzlab.demo.countriesapp.common.Resource
import com.mzzlab.demo.countriesapp.databinding.FragmentCountriesListBinding
import com.mzzlab.demo.countriesapp.model.Country
import com.mzzlab.demo.countriesapp.ui.fragment.BaseFragment
import com.mzzlab.demo.countriesapp.ui.fragment.BindingProvider
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CountriesFragment : BaseFragment<FragmentCountriesListBinding, CountriesViewModel>() {

    override val bindingProvider: BindingProvider<FragmentCountriesListBinding>
        get() = FragmentCountriesListBinding::inflate
    override val viewModel: CountriesViewModel by viewModels()

    private lateinit var adapter: CountriesRecyclerViewAdapter

    override fun initUI() {
        setupListComponents()
        setupObservables();
    }

    private fun setupObservables() {
        viewModel.getCountries().observe(viewLifecycleOwner, {
            Timber.i("getCountries observe %s", it::class.simpleName)
            when(it){
                is Resource.Loading -> setOnLoading();
                is Resource.Error -> showError(it.exception)
                is Resource.Success -> {
                    Timber.i("success list fetch: fromCache:${it.fromCache}")
                    showResultList(it.data)
                }
            }

        })
    }
    private fun setOnLoading() {
        binding.progressIndicator.visibility = View.VISIBLE
    }

    private fun showError(exception: Exception) {
        binding.swipeRefreshLayout.isRefreshing = false
        binding.progressIndicator.hide()
        showErrorBar(exception)
    }

    private fun showResultList(data: List<Country>?) {
        binding.swipeRefreshLayout.isRefreshing = false;
        binding.progressIndicator.visibility = View.GONE
        hideErrorBar()
        adapter.submitList(data)
    }

    private fun setupListComponents() {
        adapter = CountriesRecyclerViewAdapter {
            onCountrySelected(it)
        };
        val layoutManager = LinearLayoutManager(context)
        attachToRecycleView(layoutManager, adapter)

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.reload();
        }
    }

    private fun onCountrySelected(country: Country) {
        Timber.i("onCountrySelected: %s", country)
        findNavController().navigate(R.id.action_countriesFragment_to_countryDetailsFragment)
    }

    private fun attachToRecycleView(countriesLayoutManager: RecyclerView.LayoutManager,
                                    countriesAdapter:CountriesRecyclerViewAdapter){
        with(binding.list){
            layoutManager = countriesLayoutManager
            adapter = countriesAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }
}