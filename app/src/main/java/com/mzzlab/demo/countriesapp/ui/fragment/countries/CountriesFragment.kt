package com.mzzlab.demo.countriesapp.ui.fragment.countries

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        viewModel.getCountries().observe(viewLifecycleOwner, Observer {
            Timber.i("setupObservables %s", it)
            when(it){
                is Resource.Loading -> showLoader();
                is Resource.Error -> showError(it.exception)
                is Resource.Success -> showResultList(it.data)
            }

        })
    }

    private fun showLoader() {
        //TODO implement this
    }

    private fun showError(hideLoader: Exception) {

    }

    private fun showResultList(data: List<Country>?) {
        adapter.submitList(data)
    }

    private fun setupListComponents() {
        adapter = CountriesRecyclerViewAdapter();
        val layoutManager = LinearLayoutManager(context)
        attachToRecycleView(layoutManager, adapter)
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