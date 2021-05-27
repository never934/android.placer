package com.placer.domain

import com.placer.domain.entity.place.Place
import com.placer.domain.entity.place.PlaceComment
import com.placer.domain.entity.place.PlaceRequest
import com.placer.domain.entity.user.User
import java.util.*
import java.util.concurrent.ThreadLocalRandom

internal object TestUtils {
    fun randomString() : String {
        return UUID.randomUUID().toString()
    }

    fun randomDouble() : Double {
        return ThreadLocalRandom.current().nextDouble(0.0, 200.0)
    }

    fun randomLong() : Long {
        return ThreadLocalRandom.current().nextLong(0L, 1000L)
    }

    fun randomBoolean() : Boolean {
        return ThreadLocalRandom.current().nextBoolean()
    }

    fun getRandomUser() : User {
        return User(
            id = UUID.randomUUID().toString(),
            name = randomString(),
            nickname = randomString(),
            placesCount = randomLong(),
            topPosition = randomLong(),
            cityLat = randomDouble(),
            cityLng = randomDouble(),
            cityName = randomString(),
            avatarUrl = randomString(),
            registrated = true,
            createdDate = System.currentTimeMillis()
        )
    }

    fun getRandomPlaceComment(placeId: String?) : PlaceComment {
        return PlaceComment(
            id = UUID.randomUUID().toString(),
            text = randomString(),
            placeId = placeId ?: randomString(),
            createdDate = System.currentTimeMillis(),
            author = getRandomUser()
        )
    }

    fun getRandomPlace() : Place {
        return Place(
            id = UUID.randomUUID().toString(),
            name = randomString(),
            description = randomString(),
            lat = randomDouble(),
            lng = randomDouble(),
            cityName = randomString(),
            published = randomBoolean(),
            author = getRandomUser(),
            commentsCount = randomLong(),
            topPosition = randomLong(),
            photos = listOf(),
            createdDate = System.currentTimeMillis()
        )
    }

    fun getRandomPlaceRequest() : PlaceRequest {
        return PlaceRequest(
            name = randomString(),
            description = randomString(),
            lat = randomDouble(),
            lng = randomDouble(),
            published = true
        )
    }

    fun Place.toRequest() : PlaceRequest {
        return PlaceRequest(
            name, description, lat, lng, published
        )
    }

    fun List<Place>.toRequests() : List<PlaceRequest> {
        return map { it.toRequest() }
    }
}