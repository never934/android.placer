package com.placer.client.util

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth

class FirebaseUserLiveData : LiveData<String?>() {
    private val firebaseAuth = FirebaseAuth.getInstance()

    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        if (firebaseAuth.currentUser?.getIdToken(true)?.isSuccessful == true) {
            value = firebaseAuth.currentUser?.getIdToken(true)?.result?.token
        }
    }

    override fun onActive() {
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    override fun onInactive() {
        firebaseAuth.removeAuthStateListener(authStateListener)
    }
}