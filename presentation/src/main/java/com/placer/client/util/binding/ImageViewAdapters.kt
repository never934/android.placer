package com.placer.client.util.binding

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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
            .listener(object : RequestListener<Drawable>{
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    view.visibility = View.VISIBLE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    view.visibility = View.VISIBLE
                    return false
                }
            })
            .error(ContextCompat.getDrawable(view.context, R.drawable.ic_photo_placeholder))
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(view)
    }else if(photos == null){
        view.visibility = View.INVISIBLE
    }else{
        view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_photo_placeholder))
        view.visibility = View.VISIBLE
    }
}

