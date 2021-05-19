package com.placer.domain.entity.place

import com.placer.domain.entity.user.User

data class Place(
    val id: String,
    val name: String,
    val description: String?,
    val lat: Double,
    val lng: Double,
    val cityName: String?,
    val published: Boolean,
    val author: User,
    val commentsCount: Long,
    val topPosition: Long,
    val photos: List<PlacePhoto>,
    val createdDate: Long
){
    override fun hashCode(): Int {
        return 7 * name.hashCode() * author.name.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return this.id == (other as? Place)?.id
    }
}