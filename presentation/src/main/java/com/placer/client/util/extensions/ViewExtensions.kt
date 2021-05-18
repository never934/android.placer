package com.placer.client.util.extensions

import android.graphics.Typeface
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import com.placer.client.AppClass
import com.placer.client.R

object ViewExtensions {
    fun RadioButton.getFormatted() : RadioButton {
        val context = AppClass.appInstance.applicationContext
        val resources = AppClass.appInstance.resources
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
}