package com.placer.client.util.binding

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.placer.client.R

@BindingAdapter("urlDrawable")
internal fun bindUrlDrawable(view: TextView, url: String?) {
        val avatarDimen = view.resources.getDimension(R.dimen.smallest_avatar_size).toInt()
        Glide.with(view.context)
            .asBitmap()
            .load(url)
            .error(ContextCompat.getDrawable(view.context, R.drawable.ic_baseline_account_circle_24))
            .circleCrop()
            .into(object : SimpleTarget<Bitmap>(avatarDimen,avatarDimen) {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    view.setCompoundDrawablesWithIntrinsicBounds(BitmapDrawable(view.resources, resource), null, null, null)
                }
            })
}


