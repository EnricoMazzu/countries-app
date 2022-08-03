package com.mzzlab.demo.countriesapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.mzzlab.demo.countriesapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initApp()
    }

    private fun initApp() {
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}