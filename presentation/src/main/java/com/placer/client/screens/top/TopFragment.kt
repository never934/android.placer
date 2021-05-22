package com.placer.client.screens.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.placer.client.R
import com.placer.client.base.BaseFragment
import com.placer.client.base.BaseViewModel
import com.placer.client.databinding.FragmentTopBinding

internal class TopFragment : BaseFragment() {

    override val viewModel: TopViewModel by viewModels()
    private lateinit var binding: FragmentTopBinding

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_top, container, false
        )
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.show()
        return binding.root
    }

    override fun initListeners() {}

    override fun refreshStateChanged(state: Boolean) {}
}