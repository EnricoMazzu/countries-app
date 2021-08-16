package com.mzzlab.demo.countriesapp.ui.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

typealias BindingProvider<T> = (LayoutInflater, ViewGroup?, Boolean) -> T


abstract class BaseFragment<VB : ViewBinding, VM: ViewModel>: Fragment() {

    private var _binding: VB? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    protected val binding get() = _binding!!

    protected abstract val bindingProvider: BindingProvider<VB>
    protected abstract val viewModel:VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingProvider.invoke(layoutInflater, container, false);
        onBindingReady()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI();
    }

    abstract fun initUI()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null;
    }

    open fun onBindingReady(){
        //extend to implement it
    }

}