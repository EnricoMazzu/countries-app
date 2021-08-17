package com.mzzlab.demo.countriesapp.ui.fragment.countries

import androidx.recyclerview.widget.DiffUtil
import com.mzzlab.demo.countriesapp.model.Country

class CountriesDiffCallback: DiffUtil.ItemCallback<Country>() {

    override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem.code == newItem.code;
    }

    override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem.emoji == newItem.emoji && oldItem.name == newItem.name
    }
}