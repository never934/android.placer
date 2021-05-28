package com.placer.client.screens.places.update

import androidx.lifecycle.*
import com.placer.client.AppClass
import com.placer.client.R
import com.placer.client.base.BaseViewModel
import com.placer.client.entity.PlaceView
import com.placer.client.entity.toView
import com.placer.client.servicelocator.ServiceLocator
import com.placer.domain.entity.place.Place
import com.placer.domain.entity.place.PlaceRequest
import com.placer.domain.usecase.place.DeletePlaceUseCase
import com.placer.domain.usecase.place.LoadPlacesUseCase
import com.placer.domain.usecase.place.UpdatePlaceUseCase
import com.placer.domain.usecase.place.photo.DeletePlacePhotosUseCase
import com.placer.domain.usecase.place.photo.UploadPlacePhotosUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.InputStream

internal class PlaceUpdateViewModel(
    private val loadPlacesUseCase: LoadPlacesUseCase = ServiceLocator.instance.placeComponent.loadPlacesUseCase,
    private val updatePlaceUseCase: UpdatePlaceUseCase = ServiceLocator.instance.placeComponent.updatePlaceUseCase,
    private val uploadPlacePhotosUseCase: UploadPlacePhotosUseCase = ServiceLocator.instance.placePhotoComponent.uploadPlacePhotoUseCase,
    private val deletePlacePhotosUseCase: DeletePlacePhotosUseCase = ServiceLocator.instance.placePhotoComponent.deletePlacePhotoUseCase,
    private val deletePlaceUseCase: DeletePlaceUseCase = ServiceLocator.instance.placeComponent.deletePlaceUseCase,
    private var _placeId: String = ""
): BaseViewModel() {

    private var _place: MutableLiveData<Place> = MutableLiveData()
    val place: LiveData<PlaceView>
    get() = _place.map { it.toView() }

    private var _placeDeleteExecuted: MutableLiveData<Boolean> = MutableLiveData(false)
    val placeDeleteExecuted: LiveData<Boolean>
    get() = _placeDeleteExecuted

    constructor(placeId: String) : this(){
        this._placeId = placeId
        loadPlace()
    }


    fun loadPlace(){
        isLoading.value = true
        viewModelScope.launch {
            val placeResult = loadPlacesUseCase.loadPlaceById(_placeId).first()
            if(placeResult.isSuccess){
                _place.value = placeResult.getOrNull()
            }else{
                showSnackBar.value = placeResult.exceptionOrNull()?.message
            }
            isLoading.value = false
        }
    }

    fun uploadPlacePhoto(stream: InputStream){
        isLoading.value = true
        viewModelScope.launch {
            val photoIds = _place.value?.photos?.map { it.id }
            photoIds?.let {
                deletePlacePhotosUseCase.deletePlacePhotos(_placeId, photoIds).first()
            }
            val uploadPhotoResult = uploadPlacePhotosUseCase.uploadPlacePhoto(_placeId, stream.readBytes()).first()
            if(uploadPhotoResult.isSuccess){
                _place.value = uploadPhotoResult.getOrNull()
            }else{
                showSnackBar.value = uploadPhotoResult.exceptionOrNull()?.message
            }
            isLoading.value = false
        }
    }

    fun updatePlace(name: String, description: String, published: Boolean){
        if (name.isNotEmpty()) {
            isLoading.value = true
            viewModelScope.launch {
                _place.value?.let { place ->
                    val updateResult =
                        updatePlaceUseCase.updatePlace(_placeId, PlaceRequest(name, description, place.lat, place.lng, published)).first()
                    if (updateResult.isSuccess) {
                        _place.value = updateResult.getOrNull()
                        showSnackBar.value = ServiceLocator.instance.context.getString(R.string.update_place_updated_message)
                    } else {
                        showSnackBar.value = updateResult.exceptionOrNull()?.message
                    }
                }
                isLoading.value = false
            }
        }else{
            showSnackBar.value = ServiceLocator.instance.context.getString(R.string.common_err_name_empty)
        }
    }

    fun deletePlace(){
        isLoading.value = true
        viewModelScope.launch {
            val result = deletePlaceUseCase.deletePlace(_placeId).first()
            if(result.isSuccess){
                _placeDeleteExecuted.value = true
            }else{
                showSnackBar.value = result.exceptionOrNull()?.message
            }
            isLoading.value = false
        }
    }

    fun deletePlaceProcessed(){
        _placeDeleteExecuted.value = false
    }

    class Factory(
        private val placeId: String
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PlaceUpdateViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PlaceUpdateViewModel(placeId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}