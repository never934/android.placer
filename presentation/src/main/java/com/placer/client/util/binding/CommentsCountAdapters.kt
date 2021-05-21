package com.placer.client.util.binding

import androidx.databinding.BindingAdapter
import com.placer.client.customview.CommentsCountView

@BindingAdapter("commentsCount")
fun bindCommentsCount(view: CommentsCountView, count: Long?) {
    count?.let {
        view.setCount(it)
    }
}