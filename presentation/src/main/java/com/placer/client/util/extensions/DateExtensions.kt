package com.placer.client.util.extensions

import android.content.Context
import com.placer.client.AppClass
import com.placer.client.R
import com.placer.client.servicelocator.ServiceLocator
import java.text.SimpleDateFormat
import java.util.*

@Suppress("SimpleDateFormat")
object DateExtensions {

    fun Date.toView() : String {
        return SimpleDateFormat("dd MMMM yyyy").format(this)
    }

    fun Date.daysFromItRepresentation() : String {
        val days = (System.currentTimeMillis() - time)/86400000
        return String.format(ServiceLocator.instance.context.getString(R.string.days), days)
    }
}