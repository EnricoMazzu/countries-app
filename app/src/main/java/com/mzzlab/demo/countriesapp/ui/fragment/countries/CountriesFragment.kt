package com.mzzlab.demo.countriesapp.ui.fragment.countries

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mzzlab.demo.countriesapp.R
import com.mzzlab.demo.countriesapp.common.Resource
import com.mzzlab.demo.countriesapp.databinding.FragmentCountriesListBinding
import com.mzzlab.demo.countriesapp.model.Country
import com.mzzlab.demo.countriesapp.ui.fragment.BaseFragment
import com.mzzlab.demo.countriesapp.ui.fragment.BindingProvider
import com.mzzlab.demo.countriesapp.ui.fragment.details.filter.FiltersBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CountriesFragment : BaseFragment<FragmentCountriesListBinding, CountriesViewModel>() {

    override val bindingProvider: BindingProvider<FragmentCountriesListBinding>
        get() = FragmentCountriesListBinding::inflate
    override val viewModel: CountriesViewModel by viewModels()
    private var adapter: CountriesRecyclerViewAdapter? = null

    override fun initUI() {
        setHasOptionsMenu(true)
        setupListComponents()
        setupObservables();
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_countries, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filters -> {
                showFilterDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupObservables() {
        viewModel.getCountries().observe(viewLifecycleOwner, {
            Timber.i("getCountries observe %s", it::class.simpleName)
            when(it){
                is Resource.Loading -> setOnLoading();
                is Resource.Error -> it.getExceptionIfNotHandled()?.let { ex -> showError(ex)}
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
        showErrorSnack(exception)
    }

    private fun showResultList(data: List<Country>?) {
        binding.swipeRefreshLayout.isRefreshing = false;
        binding.progressIndicator.visibility = View.GONE
        hideErrorBar()
        adapter?.submitList(data)
    }

    private fun setupListComponents() {
        adapter = CountriesRecyclerViewAdapter {
            onCountrySelected(it)
        };
        val layoutManager = LinearLayoutManager(context)
        attachToRecycleView(layoutManager, adapter!!)

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.reload();
        }
    }

    private fun onCountrySelected(country: Country) {
        Timber.i("onCountrySelected: %s", country)
        var action = CountriesFragmentDirections.actionCountriesFragmentToCountryDetailsFragment(country.code)
        findNavController().navigate(action)
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

    private fun showFilterDialog(){
        FiltersBottomSheetFragment().show(childFragmentManager, "FilterDialog")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null;
    }
}