package com.placer.client.util.binding

import androidx.databinding.BindingAdapter
import com.placer.client.customview.BaseConstraintLayout

@BindingAdapter("isEnabledSwipeRefresh")
fun isEnabledSwipeRefresh(view: BaseConstraintLayout, isEnabled: Boolean?) {
    isEnabled?.let { view.swipeRefreshLayout.isEnabled = it }
}