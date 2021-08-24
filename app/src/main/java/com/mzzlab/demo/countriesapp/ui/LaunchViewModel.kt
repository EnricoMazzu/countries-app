package com.mzzlab.demo.countriesapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mzzlab.demo.countriesapp.common.Resource
import com.mzzlab.demo.countriesapp.common.isResourceLoaded
import com.mzzlab.demo.countriesapp.model.Continent
import com.mzzlab.demo.countriesapp.model.Language
import com.mzzlab.demo.countriesapp.repo.CountriesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(private val countriesRepo: CountriesRepo): ViewModel() {

    private val boot: MutableStateFlow<Resource<Nothing>> by lazy {
        MutableStateFlow(Resource.Loading())
    }

    init {
        /*viewModelScope.launch {
            val continentsFlow = countriesRepo.getContinents()
            val languagesFlow = countriesRepo.getLanguages()
            continentsFlow.combine(languagesFlow){ continentsRes: Resource<List<Continent>>, languagesRes: Resource<List<Language>> ->
                if(isResourceLoaded(continentsRes) && isResourceLoaded(languagesRes)){
                    updateValue(continentsRes, languagesRes)
                }else{
                    boot.value = Resource.Loading()
                }
            }
        }*/
    }

    private fun updateValue(
        continentsRes: Resource<List<Continent>>,
        languagesRes: Resource<List<Language>>
    ) {
        TODO("Not yet implemented")
    }

}
