package com.placer.client.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.placer.client.interfaces.PlacerFabStyle

abstract class BaseFragment : Fragment() {

    abstract val viewModel: BaseViewModel

    override fun onStart() {
        super.onStart()
        viewModel.showSnackBar.observe(this, {
            Snackbar.make(this.requireView(), it, Snackbar.LENGTH_LONG).show()
        })
        viewModel.isRefreshing.observe(this, {
            refreshStateChanged(it)
        })
        viewModel.isLoading.observe(this,{
            if (it){
                loadingStateChanged(true)
            }else{
                loadingStateChanged(false)
            }
        })
        initListeners()
    }

    abstract fun initListeners()
    abstract fun refreshStateChanged(state: Boolean)
    abstract fun loadingStateChanged(state: Boolean)
}