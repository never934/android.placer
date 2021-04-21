package com.placer.client

import android.app.Application
import android.content.Context

class AppClass : Application() {
    override fun onCreate() {
        super.onCreate()
        contextVar = applicationContext
    }

    companion object{
        private lateinit var contextVar: Context
        val context: Context
        get() = contextVar
    }
}