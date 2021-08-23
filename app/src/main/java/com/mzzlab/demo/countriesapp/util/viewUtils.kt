package com.mzzlab.demo.countriesapp.util

import android.view.View


fun View.debouncingOnClick(action: (View) -> Unit) =
    setOnClickListener(DebouncingOnClickListener(action = action))



class DebouncingOnClickListener(private val action: ((View) -> Unit)) : View.OnClickListener {

    override fun onClick(v: View) {
        if (enabled) {
            enabled = false
            v.post (ENABLE_AGAIN)
            action(v)
        }
    }

    companion object {
        @JvmStatic
        var enabled = true
        private val ENABLE_AGAIN: () -> Unit = { enabled = true }
    }
}