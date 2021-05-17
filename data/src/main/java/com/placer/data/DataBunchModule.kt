package com.placer.data

import android.content.Context

object DataBunchModule {
    lateinit var appContext: Context

    fun initDataModule(context: Context){
        appContext = context
    }

    fun getContext(): Context {
        return appContext
    }
}