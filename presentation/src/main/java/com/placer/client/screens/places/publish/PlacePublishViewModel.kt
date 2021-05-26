package com.placer.client.screens.places.publish

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.placer.client.AppClass
import com.placer.client.base.BaseViewModel
import com.placer.client.entity.PlaceView
import com.placer.client.entity.toView
import com.placer.client.util.extensions.BitmapExtensions.toByteArray
import com.placer.domain.entity.place.Place
import com.placer.domain.entity.place.PlaceRequest
import com.placer.domain.usecase.place.PublishPlaceUseCase
import com.placer.domain.usecase.place.photo.UploadPlacePhotosUseCase
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.InputStream

internal class PlacePublishViewModel(
    private val publishPlaceUseCase: PublishPlaceUseCase = AppClass.appInstance.placeComponent.publishPlacesUseCase,
    private val uploadPlacePhotosUseCase: UploadPlacePhotosUseCase = AppClass.appInstance.placePhotoComponent.uploadPlacePhotoUseCase
) : BaseViewModel() {

    var placeName: MutableLiveData<String> = MutableLiveData()
    var placeDescription: MutableLiveData<String> = MutableLiveData()
    var placePrivate: MutableLiveData<Boolean> = MutableLiveData()
    var placePhoto: MutableLiveData<Bitmap> = MutableLiveData()
    lateinit var latLng: LatLng

    private var _placePublished: MutableLiveData<PlaceView?> = MutableLiveData()
    val placePublished: LiveData<PlaceView?>
    get() = _placePublished

    fun refreshData(
        placeName: String,
        placeDescription: String,
        placePrivate: Boolean
    ){
        this.placeName.value = placeName
        this.placeDescription.value = placeDescription
        this.placePrivate.value = placePrivate
    }

    fun decodePhotoFromStream(stream: InputStream) {
        viewModelScope.launch {
            val photo = BitmapFactory.decodeStream(stream)
            placePhoto.value = photo
        }
    }

    fun publishPlace() {
        isLoading.value = true
        viewModelScope.launch {
            val request = PlaceRequest(
                name = placeName.value ?: "",
                description = placeDescription.value,
                lat = latLng.latitude,
                lng = latLng.longitude,
                published = placePrivate.value?.not() ?: false
            )
            val publishResult = publishPlaceUseCase.publishPlace(request).first()
            if (publishResult.isSuccess){
                placePhoto.value?.let {
                    publishPlacePhoto(publishResult.getOrNull())
                } ?: run {
                    _placePublished.value = publishResult.getOrNull()?.toView()
                }
            }else{
                showSnackBar.value = publishResult.exceptionOrNull()?.message
            }
            isLoading.value = false
        }
    }

    private suspend fun publishPlacePhoto(place: Place?) {
        placePhoto.value?.let { bitmap ->
            place?.let { place ->
                uploadPlacePhotosUseCase.uploadPlacePhoto(place.id, bitmap.toByteArray()).first()
                _placePublished.value = place.toView()
            }
        }
    }

    fun placePublishedUsed() {
        _placePublished.value = null
    }
}