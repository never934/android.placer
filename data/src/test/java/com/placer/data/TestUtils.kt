package com.placer.data

import com.placer.data.api.response.PlaceCommentResponse
import com.placer.data.api.response.PlaceResponse
import com.placer.data.api.response.UserResponse
import com.placer.data.db.place.PlaceDB
import com.placer.data.db.place.comment.PlaceCommentDB
import com.placer.data.db.user.UserDB
import com.placer.domain.entity.place.Place
import com.placer.domain.entity.place.PlaceComment
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
}