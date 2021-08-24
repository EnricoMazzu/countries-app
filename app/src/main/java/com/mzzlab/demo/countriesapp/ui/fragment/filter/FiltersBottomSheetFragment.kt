package com.mzzlab.demo.countriesapp.ui.fragment.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mzzlab.demo.countriesapp.R
import com.mzzlab.demo.countriesapp.common.Resource
import com.mzzlab.demo.countriesapp.databinding.BottomSheetFilterBinding
import com.mzzlab.demo.countriesapp.model.CountryFilters
import com.mzzlab.demo.countriesapp.model.Language
import com.mzzlab.demo.countriesapp.ui.fragment.countries.CountriesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FiltersBottomSheetFragment: BottomSheetDialogFragment() {
    private val noValue: CodeNamePair by lazy {
        CodeNamePair(NO_SELECTION_CODE, getString(R.string.no_value_placeholder))
    }
    private var _binding: BottomSheetFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CountriesViewModel by viewModels({requireParentFragment()})

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservable(
            viewModel.continents,
            binding.spinnerContinents,
            binding.continentProgress){ l ->
            CodeNamePair(l.code, l.name)
        }
        setupObservable(
            viewModel.languages,
            binding.spinnerLanguages,
            binding.languagesProgress){ l ->
            CodeNamePair(l.code, l.name)
        }

        binding.btnApply.setOnClickListener {
            var actualFilter = resolveFilter();
            viewModel.setFilter(actualFilter)
            this.dismiss()
        }
    }

    private fun resolveFilter(): CountryFilters {
        var itemLanguage: CodeNamePair? = binding.spinnerLanguages.selectedItem as CodeNamePair
        var itemContinent: CodeNamePair? = binding.spinnerContinents.selectedItem as CodeNamePair
        itemLanguage?.let {
            if(it.code == NO_SELECTION_CODE){
                itemLanguage = null
            }
        }
        itemContinent?.let {
            if(it.code == NO_SELECTION_CODE){
                itemContinent = null
            }
        }

        return CountryFilters(itemContinent?.code, itemLanguage?.name)
    }

    private fun <T> setupObservable(
        liveData: LiveData<Resource<List<T>>>,
        spinner: Spinner,
        loader: ProgressBar,
        mapper: (T) -> CodeNamePair
    ){
        liveData.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> makeLoading(spinner, loader)
                is Resource.Success -> onSpinnerDataAvailable(spinner,loader,it.data, mapper)
                is Resource.Error -> showError(it.getExceptionIfNotHandled())
            }
        }
    }

    private fun showError(exceptionIfNotHandled: Exception?) {
        TODO("Not yet implemented")
    }

    private fun makeLoading(spinner: Spinner, loader: ProgressBar) {
        spinner.adapter = null;
        spinner.visibility = View.INVISIBLE;
        loader.visibility = View.VISIBLE;
    }

    private fun <T> onSpinnerDataAvailable(spinner: Spinner,
                                           loader: ProgressBar,
                                           data: List<T>? ,
                                           mapper: (T) -> CodeNamePair){
        loader.visibility = View.GONE
        val adapter = createAdapter(data ?: ArrayList(), mapper)
        spinner.adapter = adapter
        spinner.visibility = View.VISIBLE;
    }

    private fun <T> createAdapter(data: List<T>, mapper: (T) -> CodeNamePair): ArrayAdapter<CodeNamePair> {
        val list = ArrayList<CodeNamePair>(data.map(mapper))
        list.add(0, noValue)
        return ArrayAdapter<CodeNamePair>(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            list
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null;
    }


    private data class CodeNamePair(val code: String, val name: String){
        override fun toString(): String {
            return name;
        }
    }

    companion object{
        const val NO_SELECTION_CODE = "NO_SELECTION_CODE"
    }

}
