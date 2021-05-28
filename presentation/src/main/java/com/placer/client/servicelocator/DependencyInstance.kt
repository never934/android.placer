package com.placer.client.servicelocator

import android.app.Application
import android.content.Context
import com.placer.data.di.component.*

interface DependencyInstance {
    val authComponent: AuthComponent
    val cityComponent: CityComponent
    val placeCommentComponent: PlaceCommentComponent
    val placeComponent: PlaceComponent
    val placePhotoComponent: PlacePhotoComponent
    val userComponent: UserComponent
    val application: Application
    val context: Context
}