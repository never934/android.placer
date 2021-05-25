package com.placer.client.adapters.cities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.placer.client.databinding.CustomviewCityItemBinding
import com.placer.domain.entity.city.City

internal class CitiesAdapter(private val clickListener: CityClickListener) : ListAdapter<City, CitiesAdapter.ViewHolder>(CitiesDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(private val binding: CustomviewCityItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: CityClickListener, item: City) {
            binding.clickListener = clickListener
            binding.city = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CustomviewCityItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}