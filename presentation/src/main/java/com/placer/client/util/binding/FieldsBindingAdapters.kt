package com.placer.client.util.binding

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.databinding.BindingAdapter
import com.placer.client.customview.ViewField

@BindingAdapter("content")
internal fun bindContent(view: ViewField, content: String?) {
    view.setContent(content)
}

@BindingAdapter("icon")
internal fun bindIcon(view: ViewField, drawable: Drawable?) {
    view.setIcon(drawable)
}

@BindingAdapter("hint")
internal fun bindHint(view: ViewField, hint: String?) {
    view.setHint(hint)
}

@BindingAdapter("contentColor")
internal fun contentColor(view: ViewField, color: Int?) {
    view.setContentColor(color)
}