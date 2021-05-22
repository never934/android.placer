package com.placer.client.screens.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.placer.client.AppClass
import com.placer.client.screens.MainActivity
import com.placer.client.R
import com.placer.client.base.BaseActivity
import com.placer.client.databinding.ActivityAuthBinding
import com.placer.data.AppPrefs

class AuthActivity: BaseActivity() {

    override val viewModel: AuthViewModel by viewModels()
    private var binding: ActivityAuthBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)
        binding?.lifecycleOwner = this
        supportActionBar?.hide()
        viewModel.loginSuccessed.observe(this, {
            if (it){
                startMainActivity()
            }
        })
        if (AppPrefs.getServerToken().isNotEmpty()){
            startMainActivity()
        }else{
            launchSignInFlow()
        }
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
                .setTheme(R.style.AuthTheme)
                .setAuthMethodPickerLayout(customLayout)
                .build(),
            SIGN_IN_RESULT_CODE
        )
    }

    private fun startMainActivity(){
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_RESULT_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                FirebaseAuth.getInstance().currentUser?.getIdToken(true)?.addOnSuccessListener {
                    viewModel.signIn(it.token ?: "")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val SIGN_IN_RESULT_CODE = 1001
    }
}