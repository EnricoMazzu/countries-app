package com.mzzlab.demo.countriesapp.ui.fragment.countries

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.mzzlab.demo.countriesapp.R
import com.mzzlab.demo.countriesapp.databinding.FragmentCountriesListBinding
import com.mzzlab.demo.countriesapp.ui.fragment.BaseFragment
import com.mzzlab.demo.countriesapp.ui.fragment.BindingProvider
import com.mzzlab.demo.countriesapp.ui.fragment.countries.placeholder.PlaceholderContent
import timber.log.Timber

/**
 * A fragment representing a list of Items.
 */
class CountriesFragment : BaseFragment<FragmentCountriesListBinding, CountriesViewModel>() {

    override val bindingProvider: BindingProvider<FragmentCountriesListBinding>
        get() = FragmentCountriesListBinding::inflate
    override val viewModel: CountriesViewModel by viewModels()
    private lateinit var adapter: CountriesRecyclerViewAdapter

    override fun initUI() {
        Timber.d("Init ui...")
        setupListComponents();
        Timber.d("init ui done")
    }

    private fun setupListComponents() {
        adapter = CountriesRecyclerViewAdapter(PlaceholderContent.ITEMS)
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