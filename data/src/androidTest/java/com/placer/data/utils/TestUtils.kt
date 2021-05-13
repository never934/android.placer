package com.placer.data.utils

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

    fun getRandomUser() : UserDB {
        return UserDB(
            id = randomString(),
            name = randomString(),
            nickname = randomString(),
            placesCount = randomLong(),
            topPosition = randomLong(),
            cityLat = randomDouble(),
            cityLng = randomDouble(),
            cityName = randomString(),
            avatarUrl = randomString(),
            createdDate = System.currentTimeMillis()
        )
    }

    fun getRandomPlaceComment(placeId: String?) : PlaceCommentDB {
        return PlaceCommentDB(
            id = randomString(),
            text = randomString(),
            placeId = placeId ?: randomString(),
            createdDate = System.currentTimeMillis(),
            author = getRandomUser()
        )
    }

}