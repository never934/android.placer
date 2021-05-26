package com.placer.client.util.binding

import android.graphics.drawable.Drawable
import androidx.databinding.BindingAdapter
import com.placer.client.customview.comments.CountView

@BindingAdapter("count")
fun bindCount(view: CountView, count: String?) {
    count?.let {
        view.setCount(it.toLong())
    }
}

@BindingAdapter("icon")
fun bindIcon(view: CountView, icon: Drawable?) {
    view.setIcon(icon)
}