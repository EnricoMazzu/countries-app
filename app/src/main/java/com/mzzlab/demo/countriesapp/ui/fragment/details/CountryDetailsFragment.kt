package com.mzzlab.demo.countriesapp.ui.fragment.details

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mzzlab.demo.countriesapp.databinding.FragmentCountryDetailsBinding
import com.mzzlab.demo.countriesapp.ui.fragment.BaseFragment
import com.mzzlab.demo.countriesapp.ui.fragment.BindingProvider
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class CountryDetailsFragment : BaseFragment<FragmentCountryDetailsBinding,CountryDetailsViewModel>() {
    override val bindingProvider: BindingProvider<FragmentCountryDetailsBinding>
        get() = FragmentCountryDetailsBinding::inflate
    override val viewModel: CountryDetailsViewModel by viewModels()

    override fun initUI() {
        viewModel.getCountryDetails().observe(viewLifecycleOwner, {
            Timber.i(">>> Result: %s", it)
        })
    }
}