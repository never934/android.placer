package com.placer.client.util.extensions

import android.graphics.Typeface
import android.widget.RadioButton
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import com.placer.client.AppClass
import com.placer.client.R
import com.placer.client.servicelocator.ServiceLocator

object ViewExtensions {
    fun RadioButton.getFormatted() : RadioButton {
        val context = ServiceLocator.instance.context
        val resources = ServiceLocator.instance.application.resources
        this.buttonDrawable = ContextCompat.getDrawable(
            context, R.drawable.radio_btn_background
        )
        this.typeface = Typeface.create("roboto_medium", Typeface.NORMAL)
        this.setPadding(
            resources.getDimension(R.dimen.default_margin).toInt(),
            resources.getDimension(R.dimen.small_margin).toInt(),
            resources.getDimension(R.dimen.small_margin).toInt(),
            resources.getDimension(R.dimen.small_margin).toInt()
        )
        return this
    }

    fun SearchView.close() {
        this.setQuery("", false)
        this.clearFocus()
        this.isIconified = true
    }
}