package com.placer.client.screens.places.publish

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.placer.client.Constants
import com.placer.client.R
import com.placer.client.base.BaseFragment
import com.placer.client.databinding.FragmentPlaceChoosePointBinding
import com.placer.client.interfaces.MyLocation
import com.placer.client.interfaces.PlacePublishTransaction
import com.placer.client.interfaces.PlacerFabStyle
import com.placer.client.util.CommonUtils
import com.placer.client.util.LocationUtils

internal class PlaceChoosePointFragment : BaseFragment(), OnMapReadyCallback, PlacerFabStyle, MyLocation, PlacePublishTransaction {

    override val viewModel: PlaceChoosePointViewModel by viewModels()
    private var binding: FragmentPlaceChoosePointBinding? = null
    private var map: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_place_choose_point, container, false
        )
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.elevation = Constants.ACTION_BAR_ELEVATION
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.show()
        initMap()
        initFabStyle()
        return binding?.root
    }

    override fun initListeners() {
        binding?.let {
            binding?.nextButton?.setOnClickListener {
                map?.cameraPosition?.let {
                    viewModel.pointChosen(it.target)
                }
            }
        }
    }

    private fun initMap(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun refreshStateChanged(state: Boolean) {
        binding?.baseConstraint?.swipeRefreshLayout?.isRefreshing = state
    }

    override fun loadingStateChanged(state: Int) {
        binding?.baseConstraint?.loadConstraint?.visibility = state
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        viewModel.mapPlaces.observe(this, {
            map.clear()
            it.forEach {  place ->
                map.addMarker(
                    MarkerOptions()
                        .position(LatLng(place.lat,place.lng))
                        .icon(CommonUtils.bitmapDescriptorFromVector(requireContext(), R.drawable.ic_map_marker))
                )
            }
        })
        viewModel.pointChosen.observe(this, {
            it?.let {
                setPlacePublishFragment(it)
                viewModel.pointChosenUsed()
            }
        })
        enableMyLocation()
    }

    override fun isLocationPermissionsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    override fun enableMyLocation() {
        if (isLocationPermissionsGranted() && viewModel.firstStart) {
            map?.isMyLocationEnabled = true
            val location = LocationUtils.getBestLocation()
            val homeLatLng = LatLng(location?.latitude ?: Constants.DEFAULT_LAT, location?.longitude ?: Constants.DEFAULT_LNG)
            val zoom = Constants.GOOGLE_MAP_CHOOSE_POINT_ZOOM
            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoom))
            viewModel.firstStart = false
        }
        else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), Constants.REQUEST_LOCATION_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        if (requestCode == Constants.REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }

    override fun initFabStyle() {
        initFabStyle(binding?.nextButton)
    }

    override fun setPlacePublishFragment(latLng: LatLng) {
        findNavController().navigate(PlaceChoosePointFragmentDirections.actionPlaceChoosePointFragmentToPlacePublishFragment(latLng))
    }
}