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
import com.mzzlab.demo.countriesapp.model.isNullOrEmpty
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

        var filter: CountryFilters = viewModel.getCurrentFilter()

        setupObservable(
            sourceData = viewModel.continents,
            spinner = binding.spinnerContinents,
            loader = binding.continentProgress,
            selectedValueCode = filter.continent
        ){ l ->
            CodeNamePair(l.code, l.name)
        }

        setupObservable(
            sourceData = viewModel.languages,
            spinner = binding.spinnerLanguages,
            loader = binding.languagesProgress,
            selectedValueCode = filter.language
        ){ l ->
            CodeNamePair(l.code, l.name)
        }


        binding.btnApply.setOnClickListener {
            val actualFilter = resolveFilter();
            viewModel.setFilter(actualFilter)
            this.dismiss()
        }

        binding.btnReset.setOnClickListener {
            binding.spinnerLanguages.setSelection(0)
            binding.spinnerContinents.setSelection(0)
        }
    }

    private fun resolveFilter(): CountryFilters {
        var itemLanguage: CodeNamePair? = binding.spinnerLanguages.selectedItem as CodeNamePair
        var itemContinent: CodeNamePair? = binding.spinnerContinents.selectedItem as CodeNamePair
        itemLanguage = itemLanguage?.nullIfNoneSelection();
        itemContinent = itemContinent?.nullIfNoneSelection();
        return CountryFilters(itemContinent?.code, itemLanguage?.code)
    }

    private fun <T> setupObservable(
        sourceData: LiveData<Resource<List<T>>>,
        spinner: Spinner,
        loader: ProgressBar,
        selectedValueCode: String?,
        mapper: (T) -> CodeNamePair
    ){
        sourceData.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> makeLoading(spinner, loader)
                is Resource.Success -> onSpinnerDataAvailable(spinner,loader,it.data, selectedValueCode, mapper)
                is Resource.Error -> showError(it.getExceptionIfNotHandled())
            }
        }
    }

    private fun showError(exceptionIfNotHandled: Exception?) {
        //TODO
    }

    private fun makeLoading(spinner: Spinner, loader: ProgressBar) {
        spinner.adapter = null;
        spinner.visibility = View.INVISIBLE;
        loader.visibility = View.VISIBLE;
    }

    private fun <T> onSpinnerDataAvailable(spinner: Spinner,
                                           loader: ProgressBar,
                                           data: List<T>? ,
                                           selectedValueCode: String?,
                                           mapper: (T) -> CodeNamePair){
        loader.visibility = View.GONE
        val spinnerList = ArrayList<CodeNamePair>(data?.map(mapper) ?: ArrayList())
        spinnerList.add(0, noValue)
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            spinnerList
        )
        spinner.adapter = adapter
        spinner.visibility = View.VISIBLE;
        spinner.setSelection(0.coerceAtLeast(spinnerList.indexOfFirst {
            it.code == selectedValueCode
        }))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null;
    }


    private data class CodeNamePair(val code: String, val name: String){
        override fun toString(): String {
            return name;
        }

        fun nullIfNoneSelection(): CodeNamePair? = if(code == NO_SELECTION_CODE){
            null
        }else{
            this
        }
    }

    companion object{
        const val NO_SELECTION_CODE = "NO_SELECTION_CODE"
    }

}
