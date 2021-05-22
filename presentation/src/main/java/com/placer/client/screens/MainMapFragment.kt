package com.placer.client.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.placer.client.Constants
import com.placer.client.R
import com.placer.client.base.BaseFragment
import com.placer.client.databinding.FragmentMainMapBinding
import com.placer.client.entity.PlaceView
import com.placer.client.interfaces.MainFieldListener
import com.placer.client.interfaces.PlacerFabStyle
import com.placer.client.navigation.PlaceViewTransaction
import com.placer.client.screens.places.PlaceViewFragmentArgs
import com.placer.client.util.CommonUtils
import com.placer.client.util.InfoWindowAdapter
import com.placer.client.util.extensions.FragmentExtensions.hideKeyBoard


internal class MainMapFragment : BaseFragment(), OnMapReadyCallback, MainFieldListener, PlacerFabStyle, PlaceViewTransaction {

    override val viewModel: MainMapViewModel by viewModels()
    private var binding: FragmentMainMapBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main_map, container, false
        )
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.hide()
        initFabStyle()
        initMap()
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadMapPlaces()
    }

    private fun initMap(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun initListeners(){
        binding?.let { binding ->
            binding.viewModel = viewModel
            binding.mainField.setListener(this, this)
            binding.drawerButton.setOnClickListener {
                (requireActivity() as MainActivity).openDrawer()
            }
            viewModel.searchPlaces.observe(this, {
                binding.mainField.setPlaces(it)
            })
            binding.baseConstraint.swipeRefreshLayout.setOnRefreshListener {
                viewModel.loadMapPlaces()
            }
        }
        viewModel.goToPlaceView.observe(this, {
            it?.let {
                setPlaceViewFragment(it)
                viewModel.navigatedToPlaceView()
            }
        })
    }

    override fun refreshStateChanged(state: Boolean) {
        binding?.baseConstraint?.swipeRefreshLayout?.isRefreshing = state
    }

    override fun onMapReady(map: GoogleMap) {
        map.uiSettings.isCompassEnabled = false
        viewModel.mapPlaces.observe(this, {
            clearMap(map)
            map.setInfoWindowAdapter(InfoWindowAdapter(requireActivity(), it))
            val markers: ArrayList<Marker> = arrayListOf()
            it.forEach { place ->
                val markerOptions = MarkerOptions()
                    .position(LatLng(place.lat, place.lng))
                    .icon(CommonUtils.bitmapDescriptorFromVector(requireContext(), R.drawable.ic_map_marker))
                    .title(place.id)
                markerOptions.infoWindowAnchor(markerOptions.infoWindowAnchorU, Constants.GOOGLE_MAP_INFO_WINDOW_V_ANCHOR)
                val marker = map.addMarker(markerOptions)
                markers.add(marker)
            }
            viewModel.updateMapMarkers(markers)
            executeInitPlace(map)
        })
        map.setOnMarkerClickListener {
            openMarker(map, it)
            true
        }
        map.setOnInfoWindowClickListener { marker ->
            viewModel.placeClicked(marker.title)
        }
    }

    private fun clearMap(map: GoogleMap){
        map.clear()
        viewModel.updateMapMarkers(arrayListOf())
    }

    private fun executeInitPlace(map: GoogleMap){
        if (arguments != null){
            val initPlace = PlaceViewFragmentArgs.fromBundle(requireArguments()).place
            val initMarker = viewModel.mapMarkers.firstOrNull{ it.title == initPlace.id }
            openMarker(map, initMarker)
            arguments = null
        }
    }

    private fun openMarker(map: GoogleMap, marker: Marker?) {
        marker?.let {
            map.animateCamera(
                CameraUpdateFactory.newLatLng(
                    CommonUtils.getMapFocusPoint(map, requireView(), marker)
                ),
                Constants.GOOGLE_MAP_ANIMATION_DURATION,
                null
            )
            marker.showInfoWindow()
        }
    }

    override fun textInMainFieldChanged(text: String) {
        viewModel.loadPlaces(text)
    }

    override fun mainFieldFocusChanged(hasFocus: Boolean) {
        if (hasFocus){
            binding?.drawerButton?.visibility = View.GONE
        }else{
            binding?.drawerButton?.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun showMyPoints() {
        viewModel.showMyPoints()
    }

    override fun showAllPoints() {
        viewModel.showAllMapPoints()
    }

    override fun placeSelected(place: PlaceView) {
        hideKeyBoard()
        viewModel.placeClicked(place)
    }

    override fun initFabStyle() {
        initFabStyle(binding?.addButton)
    }

    override fun setPlaceViewFragment(place: PlaceView) {
        findNavController().navigate(MainMapFragmentDirections.actionMainMapFragmentToPlaceViewFragment(place))
    }

}