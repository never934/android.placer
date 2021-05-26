package com.placer.client.screens.places

import androidx.lifecycle.*
import com.placer.client.AppClass
import com.placer.client.base.BaseViewModel
import com.placer.client.entity.PlaceCommentView
import com.placer.client.entity.PlaceView
import com.placer.client.entity.toView
import com.placer.client.entity.toViews
import com.placer.data.AppPrefs
import com.placer.domain.entity.place.Place
import com.placer.domain.entity.place.PlaceComment
import com.placer.domain.usecase.place.LoadPlacesUseCase
import com.placer.domain.usecase.place.comment.LoadPlaceCommentsUseCase
import com.placer.domain.usecase.place.comment.PublishPlaceCommentUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

internal class PlaceViewViewModel(
    private val loadPlacesUseCase: LoadPlacesUseCase = AppClass.appInstance.placeComponent.loadPlacesUseCase,
    private val loadPlaceCommentsUseCase: LoadPlaceCommentsUseCase = AppClass.appInstance.placeCommentComponent.loadPlaceCommentUseCase,
    private val publishPlaceCommentUseCase: PublishPlaceCommentUseCase = AppClass.appInstance.placeCommentComponent.publishPlaceCommentUseCase,
    private var _placeId: String = ""
    ) : BaseViewModel()
{

    constructor(placeId: String) : this(){
        this._placeId = placeId
        loadPlace()
        loadPlaceComments()
    }

    private var _place: MutableLiveData<Place> = MutableLiveData()
    val place: LiveData<PlaceView>
    get() = _place.map { it.toView() }

    private var _placeComments: MutableLiveData<List<PlaceComment>> = MutableLiveData()
    val placeComments: LiveData<List<PlaceCommentView>>
    get() = _placeComments.map { it.toViews() }

    private var _clientIsPlaceAuthor: MutableLiveData<Boolean> = MutableLiveData()
    val clientIsPlaceAuthor: LiveData<Boolean>
    get() = _clientIsPlaceAuthor

    private var _showPlaceOnMapExecute: MutableLiveData<PlaceView> = MutableLiveData()
    val showPlaceOnMapExecute: LiveData<PlaceView>
    get() = _showPlaceOnMapExecute

    val placeId: String
    get() = _placeId

    fun loadPlace() {
        isRefreshing.value = true
        viewModelScope.launch {
            val result = loadPlacesUseCase.loadPlaceById(_placeId).first()
            if(result.isSuccess){
                _place.value = result.getOrNull()
                _clientIsPlaceAuthor.value = _place.value?.author?.id == AppPrefs.getUserId()
            }else{
                showSnackBar.value = result.exceptionOrNull()?.message
            }
            isRefreshing.value = false
        }
    }

    fun loadPlaceComments() {
        viewModelScope.launch {
            val result = loadPlaceCommentsUseCase.loadPlaceComments(_placeId).first()
            if(result.isSuccess){
                _placeComments.value = result.getOrNull()
            }else{
                showSnackBar.value = result.exceptionOrNull()?.message
            }
        }
    }

    fun sendPlaceComment(commentText: String) {
        viewModelScope.launch {
            val result = publishPlaceCommentUseCase.publishPlaceComment(_placeId, commentText).first()
            if(result.isSuccess){
                _placeComments.value = result.getOrNull()
                loadPlace()
            }else{
                showSnackBar.value = result.exceptionOrNull()?.message
            }
        }
    }

    fun showPlaceOnMap(){
        _showPlaceOnMapExecute.value = _place.value?.toView()
    }

    fun showPlaceOnExecuted(){
        _showPlaceOnMapExecute.value = null
    }

    class Factory(
        private val placeId: String
        ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PlaceViewViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PlaceViewViewModel(placeId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}