package com.placer.data.api.response

import com.placer.domain.entity.city.City

data class CityResponse(
    val city: String,
    val latitude: Double,
    val longitude: Double
)

fun List<CityResponse>.toEntities() : List<City> {
    return map {
        City(
            city = it.city,
            latitude = it.latitude,
            longitude = it.longitude
        )
    }
}
