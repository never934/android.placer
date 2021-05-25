package com.placer.client.adapters.cities

import com.placer.domain.entity.city.City

internal class CityClickListener(val clickListener: (city: City) -> Unit) {
    fun onClick(city: City) = clickListener(city)
}