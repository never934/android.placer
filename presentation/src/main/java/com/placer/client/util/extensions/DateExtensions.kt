package com.placer.client.util.extensions

import com.placer.client.AppClass
import com.placer.client.R
import java.text.SimpleDateFormat
import java.util.*

@Suppress("SimpleDateFormat")
object DateExtensions {

    fun Date.toView() : String {
        return SimpleDateFormat("dd MMMM yyyy").format(this)
    }

    fun Date.daysFromItRepresentation() : String {
        val days = (System.currentTimeMillis() - time)/86400000
        return String.format(AppClass.context.getString(R.string.days), days)
    }
}