package com.placer.client.util.binding

import android.graphics.drawable.Drawable
import androidx.databinding.BindingAdapter
import com.placer.client.customview.TextButton

@BindingAdapter("text")
internal fun bindText(view: TextButton, text: String?) {
    view.setText(text)
}

@BindingAdapter("icon")
internal fun bindIcon(view: TextButton, icon: Drawable?) {
    view.setIcon(icon)
}