package com.placer.data

import android.os.Build.VERSION_CODES.Q
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.placer.data.api.response.*
import com.placer.data.db.place.comment.toEntity
import com.placer.data.db.place.toEntity
import com.placer.data.db.user.toEntity
import com.placer.domain.entity.place.Place
import com.placer.domain.entity.place.PlaceComment
import com.placer.domain.entity.user.User
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.util.*

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Q])
internal class ConverterTests {
    @Test
    fun convertPlaceResponseToDbAndToEntity(){
        // Given

        val data = PlaceResponse(
            id = UUID.randomUUID().toString(),
            name = TestUtils.randomString(),
        description = TestUtils.randomString(),
        lat = TestUtils.randomDouble(),
        lng = TestUtils.randomDouble(),
        cityName = TestUtils.randomString(),
        published = TestUtils.randomBoolean(),
        author = UserResponse(
            id = UUID.randomUUID().toString(),
            name = TestUtils.randomString(),
            nickname = TestUtils.randomString(),
            placesCount = TestUtils.randomLong(),
            topPosition = TestUtils.randomLong(),
            cityLat = TestUtils.randomDouble(),
            cityLng = TestUtils.randomDouble(),
            cityName = TestUtils.randomString(),
            avatarUrl = TestUtils.randomString(),
            registrated = true,
            createdDate = System.currentTimeMillis()
        ),
        commentsCount = TestUtils.randomLong(),
        topPosition = TestUtils.randomLong(),
        photos = listOf(),
        createdDate = System.currentTimeMillis()
        )

        val dataEntity = Place(
            id = data.id,
            name = data.name,
            description = data.description,
            lat = data.lat,
            lng = data.lng,
            cityName = data.cityName,
            published = data.published,
            author = User(
                id = data.author.id,
                name = data.author.name,
                nickname = data.author.nickname,
                placesCount = data.author.placesCount,
                topPosition = data.author.topPosition,
                cityLat = data.author.cityLat,
                cityLng = data.author.cityLng,
                cityName = data.author.cityName,
                avatarUrl = data.author.avatarUrl,
                registrated = data.author.registrated,
                createdDate = data.author.createdDate
            ),
            commentsCount = data.commentsCount,
            topPosition = data.topPosition,
            photos = listOf(),
            createdDate = data.createdDate
        )

        // When
        val convertedToDB = data.toDB()
        val convertedToEntity = convertedToDB.toEntity()

        // Then
        MatcherAssert.assertThat(convertedToEntity == dataEntity, `is`(true))
    }

    @Test
    fun convertPlaceCommentResponseToDbAndToEntity(){
        // Given
        val data = PlaceCommentResponse(
            id = UUID.randomUUID().toString(),
            text = TestUtils.randomString(),
            placeId = TestUtils.randomString(),
            createdDate = System.currentTimeMillis(),
            author = UserResponse(
                id = UUID.randomUUID().toString(),
                name = TestUtils.randomString(),
                nickname = TestUtils.randomString(),
                placesCount = TestUtils.randomLong(),
                topPosition = TestUtils.randomLong(),
                cityLat = TestUtils.randomDouble(),
                cityLng = TestUtils.randomDouble(),
                cityName = TestUtils.randomString(),
                avatarUrl = TestUtils.randomString(),
                registrated = true,
                createdDate = System.currentTimeMillis()
            )
        )

        val dataEntity = PlaceComment(
            id = data.id,
            text = data.text,
            placeId = data.placeId,
            createdDate = data.createdDate,
            author = User(
                id = data.author.id,
                name = data.author.name,
                nickname = data.author.nickname,
                placesCount = data.author.placesCount,
                topPosition = data.author.topPosition,
                cityLat = data.author.cityLat,
                cityLng = data.author.cityLng,
                cityName = data.author.cityName,
                avatarUrl = data.author.avatarUrl,
                registrated = true,
                createdDate = data.author.createdDate
            )
        )

        // When
        val convertedToDB = data.toDB()
        val convertedToEntity = convertedToDB.toEntity()

        // Then
        MatcherAssert.assertThat(convertedToEntity == dataEntity, `is`(true))
    }

    @Test
    fun convertUserResponseToDbAndToEntity(){
        // Given
        val data = UserResponse(
            id = UUID.randomUUID().toString(),
            name = TestUtils.randomString(),
            nickname = TestUtils.randomString(),
            placesCount = TestUtils.randomLong(),
            topPosition = TestUtils.randomLong(),
            cityLat = TestUtils.randomDouble(),
            cityLng = TestUtils.randomDouble(),
            cityName = TestUtils.randomString(),
            avatarUrl = TestUtils.randomString(),
            registrated = true,
            createdDate = System.currentTimeMillis()
        )

        val dataEntity = User(
            id = data.id,
            name = data.name,
            nickname = data.nickname,
            placesCount = data.placesCount,
            topPosition = data.topPosition,
            cityLat = data.cityLat,
            cityLng = data.cityLng,
            cityName = data.cityName,
            avatarUrl = data.avatarUrl,
            registrated = data.registrated,
            createdDate = data.createdDate
        )

        // When
        val convertedToDB = data.toDB()
        val convertedToEntity = convertedToDB.toEntity()

        // Then
        MatcherAssert.assertThat(convertedToEntity == dataEntity, `is`(true))
    }
}