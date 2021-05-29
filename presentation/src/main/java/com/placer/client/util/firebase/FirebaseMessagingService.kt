package com.placer.client.util.firebase

import com.google.firebase.messaging.FirebaseMessagingService
import com.placer.data.AppPrefs

class FirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        AppPrefs.saveFcmToken(token)
    }

}