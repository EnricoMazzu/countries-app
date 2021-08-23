package com.mzzlab.demo.countriesapp.ui.fragment.countries

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mzzlab.demo.countriesapp.databinding.FragmentCountriesBinding
import com.mzzlab.demo.countriesapp.model.Country
import com.mzzlab.demo.countriesapp.util.debouncingOnClick

typealias CountrySelectionListener = (Country) -> Unit

class CountriesRecyclerViewAdapter(private val selectionListener: CountrySelectionListener? = null)
    : ListAdapter<Country, CountriesRecyclerViewAdapter.ViewHolder>(CountriesDiffCallback()) {

    inner class ViewHolder(binding: FragmentCountriesBinding, private val selectionListener: CountrySelectionListener? = null) :
        RecyclerView.ViewHolder(binding.root) {
        private val emojiView = binding.emojiView;
        private val lblCountryName = binding.lblCountryName
        private val lblCode = binding.lblCode;
        //private val idView: TextView = binding.itemNumber
        //private val contentView: TextView = binding.content
        private var country: Country? = null

        init {
            binding.root.debouncingOnClick {
                country?.let {
                    selectionListener?.invoke(it)
                }
            }
        }

        fun bind(country: Country){
            this.country = country;
            emojiView.text = country.emoji
            lblCountryName.text = country.name
            lblCode.text = country.code
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentCountriesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), selectionListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}