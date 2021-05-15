package com.placer.client.screens

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.placer.client.AppClass
import com.placer.client.R
import com.placer.client.base.BaseActivity
import com.placer.client.databinding.ActivityAuthBinding

class AuthActivity: BaseActivity() {

    lateinit var viewModel: AuthViewModel
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)
        binding.lifecycleOwner = this
        launchSignInFlow()
    }

    private fun launchSignInFlow() {
        val customLayout = AuthMethodPickerLayout.Builder(R.layout.layout_auth)
            .setGoogleButtonId(R.id.googleBtn)
            .setEmailButtonId(R.id.emailBtn)
            .build()
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setAuthMethodPickerLayout(customLayout)
                .build(),
            SIGN_IN_RESULT_CODE
        )
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this,
            AuthViewModel.Factory(
                AppClass.appInstance.authComponent.signInUseCase
            )
        )
            .get(AuthViewModel::class.java)
        _viewModel = viewModel
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_RESULT_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Log.e("login","login ok")
                Log.e("login", FirebaseAuth.getInstance().currentUser?.email ?: "null")
                FirebaseAuth.getInstance().currentUser?.getIdToken(true)?.addOnSuccessListener {
                    viewModel.signIn(it.token ?: "")
                }
            }
        }
    }

    companion object {
        const val SIGN_IN_RESULT_CODE = 1001
    }
}