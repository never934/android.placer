package com.placer.client.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.placer.client.MainActivity
import com.placer.client.R
import com.placer.client.databinding.FragmentMainMapBinding


class MainMapFragment : Fragment(), OnMapReadyCallback {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentMainMapBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main_map, container, false
        )
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.hide()
        binding.drawerButton.setOnClickListener {
            (requireActivity() as MainActivity).openDrawer()
        }
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return binding.root
    }

    override fun onMapReady(p0: GoogleMap) {
    }
}