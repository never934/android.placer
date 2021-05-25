package com.placer.client.adapters.cities

import androidx.recyclerview.widget.DiffUtil
import com.placer.domain.entity.city.City

internal class CitiesDiffCallback : DiffUtil.ItemCallback<City>() {
    override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
        return (oldItem.city == newItem.city) && (oldItem.country == newItem.country)
    }

    override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem == newItem
    }
}