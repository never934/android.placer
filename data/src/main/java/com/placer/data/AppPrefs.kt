package com.placer.data

import android.content.Context

object AppPrefs {
    fun saveServerToken(token: String){
        DataBunchModule.getContext()
            .getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)
            .edit()
            .putString(Constants.SERVER_TOKEN_ID_IN_SETTINGS, token)
            .apply()
    }

    fun getServerToken() : String {
        return DataBunchModule.getContext()
            .getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)
            .getString(Constants.SERVER_TOKEN_ID_IN_SETTINGS, "") ?: ""
    }
}