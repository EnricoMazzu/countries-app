package com.mzzlab.demo.countriesapp.ui.fragment.countries

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mzzlab.demo.countriesapp.databinding.FragmentCountriesBinding
import com.mzzlab.demo.countriesapp.model.Country

class CountriesRecyclerViewAdapter
    : ListAdapter<Country, CountriesRecyclerViewAdapter.ViewHolder>(CountriesDiffCallback()) {

    inner class ViewHolder(binding: FragmentCountriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentCountriesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.idView.text = item.code
        holder.contentView.text = item.name
    }


}