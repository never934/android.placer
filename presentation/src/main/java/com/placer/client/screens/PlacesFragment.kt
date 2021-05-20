package com.placer.client.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.placer.client.R
import com.placer.client.base.BaseFragment
import com.placer.client.databinding.FragmentMainMapBinding

class PlacesFragment : BaseFragment() {

    private lateinit var viewModel: MainMapViewModel
    private var binding: FragmentMainMapBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main_map, container, false
        )
        return binding?.root
    }

    override fun initViewModel() {
        TODO("Not yet implemented")
    }

    override fun initListeners() {
        TODO("Not yet implemented")
    }
}