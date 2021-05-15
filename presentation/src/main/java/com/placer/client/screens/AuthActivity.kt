package com.placer.client.screens

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
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
        viewModel.authenticationState.observe(this, {
            it?.let{
                viewModel.signIn(it)
            }
        }
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

}