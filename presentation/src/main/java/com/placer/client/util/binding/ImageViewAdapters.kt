package com.placer.client.util.binding

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.placer.client.AppClass
import com.placer.client.R

@BindingAdapter("imageUrl")
fun bindImageUrl(view: ImageView, url: String?) {
        Glide.with(AppClass.appInstance.applicationContext)
            .load(url)
            .error(ContextCompat.getDrawable(AppClass.appInstance.applicationContext, R.drawable.ic_baseline_account_circle_24))
            .circleCrop()
            .into(view)
}