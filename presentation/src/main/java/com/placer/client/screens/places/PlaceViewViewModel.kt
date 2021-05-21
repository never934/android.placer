package com.placer.client.screens.places

import androidx.lifecycle.*
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
    private val loadPlacesUseCase: LoadPlacesUseCase,
    private val loadPlaceCommentsUseCase: LoadPlaceCommentsUseCase,
    private val publishPlaceCommentUseCase: PublishPlaceCommentUseCase,
    private val placeId: String
    ) : BaseViewModel()
{
    private var _place: MutableLiveData<Place> = MutableLiveData()
    val place: LiveData<PlaceView>
    get() = _place.map { it.toView() }

    private var _placeComments: MutableLiveData<List<PlaceComment>> = MutableLiveData()
    val placeComments: LiveData<List<PlaceCommentView>>
    get() = _placeComments.map { it.toViews() }

    private var _clientIsPlaceAuthor: MutableLiveData<Boolean> = MutableLiveData()
    val clientIsPlaceAuthor: LiveData<Boolean>
    get() = _clientIsPlaceAuthor

    init {
        loadPlace()
        loadPlaceComments()
    }

    fun loadPlace() {
        isRefreshing.value = true
        viewModelScope.launch {
            val result = loadPlacesUseCase.loadPlaceById(placeId).first()
            if(result.isSuccess){
                _place.value = result.getOrNull()
                _clientIsPlaceAuthor.value = _place.value?.author?.id == AppPrefs.getUserId()
            }else{
                showSnackBar.value = result.exceptionOrNull()?.message
            }
            isRefreshing.value = false
        }
    }

    private fun loadPlaceComments() {
        viewModelScope.launch {
            val result = loadPlaceCommentsUseCase.loadPlaceComments(placeId).first()
            if(result.isSuccess){
                _placeComments.value = result.getOrNull()
            }else{
                showSnackBar.value = result.exceptionOrNull()?.message
            }
        }
    }

    fun sendPlaceComment(commentText: String) {
        viewModelScope.launch {
            val result = publishPlaceCommentUseCase.publishPlaceComment(placeId, commentText).first()
            if(result.isSuccess){
                _placeComments.value = result.getOrNull()
                loadPlace()
            }else{
                showSnackBar.value = result.exceptionOrNull()?.message
            }
        }
    }

    class Factory(
        private val placesUseCase: LoadPlacesUseCase,
        private val loadPlaceCommentsUseCase: LoadPlaceCommentsUseCase,
        private val publishPlaceCommentUseCase: PublishPlaceCommentUseCase,
        private val placeId: String
        ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PlaceViewViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PlaceViewViewModel(placesUseCase, loadPlaceCommentsUseCase, publishPlaceCommentUseCase, placeId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}