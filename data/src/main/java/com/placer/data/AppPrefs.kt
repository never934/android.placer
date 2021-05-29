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

    fun saveUserId(userId: String){
        DataBunchModule.getContext()
            .getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)
            .edit()
            .putString(Constants.USER_ID_IN_SETTINGS, userId)
            .apply()
    }

    fun getUserId() : String {
        return DataBunchModule.getContext()
            .getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)
            .getString(Constants.USER_ID_IN_SETTINGS, "") ?: ""
    }

    fun saveFcmToken(token: String){
        DataBunchModule.getContext()
            .getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)
            .edit()
            .putString(Constants.FCM_TOKEN_ID_IN_SETTINGS, token)
            .apply()
    }

    fun getFcmToken() : String? {
        return DataBunchModule.getContext()
            .getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)
            .getString(Constants.FCM_TOKEN_ID_IN_SETTINGS, "")
    }
}