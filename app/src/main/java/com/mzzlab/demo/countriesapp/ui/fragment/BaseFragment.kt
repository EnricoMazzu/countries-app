package com.mzzlab.demo.countriesapp.ui.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.mzzlab.demo.countriesapp.R
import com.mzzlab.demo.countriesapp.api.ApiException
import com.mzzlab.demo.countriesapp.api.ErrorCode
import com.mzzlab.demo.countriesapp.api.asApiException

typealias BindingProvider<T> = (LayoutInflater, ViewGroup?, Boolean) -> T


abstract class BaseFragment<VB : ViewBinding, VM: ViewModel>: Fragment() {

    private var errorSnack: Snackbar? = null
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
    }

    abstract fun initUI()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null;
    }


    protected fun showErrorSnack(exception: Exception,
                                 @StringRes actionTextRes:Int = R.string.err_something_went_wrong_message,
                                 retryAction: ((v:View) -> Unit)? = null) {
        val translated = exception.asApiException();
        val message = resolveErrorRes(translated.code, actionTextRes)
        errorSnack = with(Snackbar.make(requireView(), message, Snackbar.LENGTH_INDEFINITE)) {
            animationMode = Snackbar.ANIMATION_MODE_SLIDE
            if(retryAction != null){
                setAction(actionTextRes, retryAction)
            }else{
                setAction(getString(R.string.err_close_action_text)){}
            }
            show()
            this
        }
    }


    protected fun isErrorShowed(): Boolean {
        return errorSnack?.isShown ?: false
    }

    protected fun hideErrorBar(){
        errorSnack?.let {
            if (it.isShown) {
                it.dismiss()
            }
        }
    }

    companion object {
        @JvmStatic
        val ERROR_RES_MAPPER = mapOf (
            ErrorCode.GENERIC_ERROR to R.string.err_something_went_wrong_message,
            ErrorCode.NETWORK_ERROR to R.string.err_network_message,
            ErrorCode.REMOTE_SERVICE_ERROR to R.string.err_remote_service_message,
            ErrorCode.CACHE_MISS_ERROR to R.string.err_network_message,
            ErrorCode.ENCODING_EXCEPTION to R.string.err_something_went_wrong_message,
        )

        fun resolveErrorRes(code:ErrorCode, fallback: Int = R.string.err_something_went_wrong_message): Int {
            return ERROR_RES_MAPPER[code] ?: fallback
        }
    }
}

