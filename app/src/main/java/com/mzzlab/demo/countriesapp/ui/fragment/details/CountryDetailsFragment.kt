package com.mzzlab.demo.countriesapp.ui.fragment.details

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mzzlab.demo.countriesapp.R
import com.mzzlab.demo.countriesapp.common.Resource
import com.mzzlab.demo.countriesapp.databinding.FragmentCountryDetailsBinding
import com.mzzlab.demo.countriesapp.model.CountryDetails
import com.mzzlab.demo.countriesapp.ui.fragment.BaseFragment
import com.mzzlab.demo.countriesapp.ui.fragment.BindingProvider
import com.mzzlab.demo.countriesapp.ui.fragment.countries.CountriesFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class CountryDetailsFragment : BaseFragment<FragmentCountryDetailsBinding,CountryDetailsViewModel>() {
    override val bindingProvider: BindingProvider<FragmentCountryDetailsBinding>
        get() = FragmentCountryDetailsBinding::inflate
    override val viewModel: CountryDetailsViewModel by viewModels()
    private val notAvailableValue: String by lazy { getString(R.string.label_data_not_available) }

    private val detailsArgs: CountryDetailsFragmentArgs by navArgs()

    override fun initUI() {
        viewModel.setCountryCode(detailsArgs.countryCode)

        viewModel.getCountryDetails().observe(viewLifecycleOwner, {
            Timber.d("getCountryDetails %s", it)
            when(it){
                is Resource.Loading-> onLoading()
                is Resource.Success -> showCountryInfo(it.data!!)
                is Resource.Error -> it.getExceptionIfNotHandled()?.let { ex -> showError(ex) }
            }
        })
    }

    private fun onLoading() {
        binding.countryLoadProgress?.show()
    }

    private fun showCountryInfo(data: CountryDetails) {
        binding.countryLoadProgress?.hide()
        binding.emojiFlag.text = data.emoji
        binding.lblCountryName.text = data.name
        binding.lblCapital.text = data.capital ?: notAvailableValue
        binding.lblContinent.text = data.continent
        binding.lblPhone.text = data.phone
        binding.lblCurrency.text = data.currency  ?: notAvailableValue
        val languages = if(data.languages.isNotEmpty()){
            data.languages.joinToString(" , ")
        }else{
            notAvailableValue
        }
        binding.lblLanguages.text = languages
    }


    private fun showError(exception: Exception) {
        Timber.e(exception,"error: %s", exception.message)
        binding.countryLoadProgress?.hide()
        showErrorSnack(exception, R.string.err_retry_text){
            viewModel.reload();
        }
    }
}