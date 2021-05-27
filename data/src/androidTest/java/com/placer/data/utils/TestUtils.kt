package com.placer.data.utils

import com.placer.data.db.place.PlaceDB
import com.placer.data.db.place.comment.PlaceCommentDB
import com.placer.data.db.user.UserDB
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

    fun getRandomUserDB(authorId: String?) : UserDB {
        return UserDB(
            id = authorId ?: UUID.randomUUID().toString(),
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

    fun getRandomPlaceCommentDB(placeId: String?) : PlaceCommentDB {
        return PlaceCommentDB(
            id = UUID.randomUUID().toString(),
            text = randomString(),
            placeId = placeId ?: randomString(),
            createdDate = System.currentTimeMillis(),
            author = getRandomUserDB(null)
        )
    }

    fun getRandomPlaceDB(authorId: String?) : PlaceDB {
        return PlaceDB(
            id = UUID.randomUUID().toString(),
            name = randomString(),
            description = randomString(),
            lat = randomDouble(),
            lng = randomDouble(),
            cityName = randomString(),
            published = randomBoolean(),
            author = getRandomUserDB(authorId),
            commentsCount = randomLong(),
            topPosition = randomLong(),
            photos = listOf(),
            createdDate = System.currentTimeMillis()
        )
    }

}