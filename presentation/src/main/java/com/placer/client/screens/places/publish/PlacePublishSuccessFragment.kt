package com.placer.client.screens.places.publish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.placer.client.Constants
import com.placer.client.R
import com.placer.client.base.BaseFragment
import com.placer.client.databinding.FragmentPlacePublishSuccessBinding

internal class PlacePublishSuccessFragment : BaseFragment() {

    override val viewModel: PlacePublishSuccessViewModel by viewModels()
    private var binding: FragmentPlacePublishSuccessBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_place_publish_success, container, false
        )
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.elevation = Constants.ACTION_BAR_ELEVATION
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.show()
        return binding?.root
    }

    override fun initListeners() {
        viewModel.profile.observe(this, {
            binding?.user = it
            binding?.executePendingBindings()
            binding?.placesCountView?.visibility = View.VISIBLE
        })
        binding?.okButton?.setOnClickListener { requireActivity().finish() }
    }

    override fun refreshStateChanged(state: Boolean) {
        binding?.baseConstraint?.swipeRefreshLayout?.isRefreshing = state
    }

    override fun loadingStateChanged(state: Int) {
        binding?.baseConstraint?.loadConstraint?.visibility = state
    }
}