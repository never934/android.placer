package com.placer.data.api.response

import com.placer.domain.entity.city.City

internal data class CityResponse(
    val city: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)

internal fun List<CityResponse>.toEntities() : List<City> {
    return map {
        City(
            city = it.city,
            country = it.country,
            latitude = it.latitude,
            longitude = it.longitude
        )
    }
}
