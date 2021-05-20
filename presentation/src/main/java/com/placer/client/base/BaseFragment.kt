package com.placer.client.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.placer.client.interfaces.PlacerFabStyle

abstract class BaseFragment : Fragment() {

    var _viewModel: BaseViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onStart() {
        super.onStart()
        _viewModel?.showSnackBar?.observe(this, {
            Snackbar.make(this.requireView(), it, Snackbar.LENGTH_LONG).show()
        })
        _viewModel?.isRefreshing?.observe(this, {
            refreshStateChanged(it)
        })
        initListeners()
    }

    abstract fun initViewModel()
    abstract fun initListeners()
    abstract fun refreshStateChanged(state: Boolean)
}