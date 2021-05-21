package com.placer.client.util.binding

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.imageview.ShapeableImageView
import com.placer.client.AppClass
import com.placer.client.R
import com.placer.client.entity.PlacePhotoView
import com.placer.domain.entity.place.PlacePhoto

@BindingAdapter("avatarUrl")
internal fun bindAvatarUrl(view: ImageView, url: String?) {
        Glide.with(view.context)
            .load(url)
            .error(ContextCompat.getDrawable(view.context, R.drawable.ic_baseline_account_circle_24))
            .circleCrop()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(view)
}

@BindingAdapter("placePhotoPreview")
internal fun bindPlacePhotoPreview(view: ShapeableImageView, photos: List<PlacePhotoView>?) {
    if (photos?.isNullOrEmpty()?.not() == true){
        Glide.with(view.context)
            .load(photos.first().url)
            .error(ContextCompat.getDrawable(view.context, R.drawable.ic_photo_placeholder))
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(view)
    }else{
        view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_photo_placeholder))
    }
}

@BindingAdapter("placePhotoPreview")
internal fun bindPlacePhotoPreview(view: ImageView, photos: List<PlacePhotoView>?) {
    if (photos?.isNullOrEmpty()?.not() == true){
        Glide.with(view.context)
            .load(photos.first().url)
            .error(ContextCompat.getDrawable(view.context, R.drawable.ic_photo_placeholder))
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(view)
    }else{
        view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_photo_placeholder))
    }
}

