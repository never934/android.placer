package com.placer.client.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.placer.client.AppClass
import com.placer.client.Constants
import com.placer.client.R
import com.placer.client.base.BaseFragment
import com.placer.client.databinding.FragmentMainMapBinding
import com.placer.client.entity.PlaceView
import com.placer.client.interfaces.MainFieldListener
import com.placer.client.interfaces.PlacerFabStyle
import com.placer.client.navigation.PlaceViewTransaction
import com.placer.client.util.CommonUtils
import com.placer.client.util.InfoWindowAdapter
import com.placer.client.util.extensions.FragmentExtensions.hideKeyBoard


internal class MainMapFragment : BaseFragment(), OnMapReadyCallback, MainFieldListener, PlacerFabStyle, PlaceViewTransaction {

    private lateinit var viewModel: MainMapViewModel
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
            map.clear()
            map.setInfoWindowAdapter(InfoWindowAdapter(requireActivity(), it))
            it.forEach { place ->
                val marker = MarkerOptions()
                    .position(LatLng(place.lat, place.lng))
                    .icon(CommonUtils.bitmapDescriptorFromVector(requireContext(), R.drawable.ic_map_marker))
                    .title(place.id)
                marker.infoWindowAnchor(marker.infoWindowAnchorU, Constants.GOOGLE_MAP_INFO_WINDOW_V_ANCHOR)
                map.addMarker(marker)
            }
        })
        map.setOnMarkerClickListener {
            map.animateCamera(
                CameraUpdateFactory.newLatLng(
                    CommonUtils.getMapFocusPoint(map, requireView(), it)
                ),
                Constants.GOOGLE_MAP_ANIMATION_DURATION,
                null
            )
            it.showInfoWindow()
            true
        }
        map.setOnInfoWindowClickListener { marker ->
            viewModel.placeClicked(marker.title)
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

    override fun initViewModel() {
        viewModel = ViewModelProvider(this,
            MainMapViewModel.Factory(
                AppClass.appInstance.placeComponent.loadPlacesUseCase
            )
        )
            .get(MainMapViewModel::class.java)
        _viewModel = viewModel
    }

    override fun initFabStyle() {
        initFabStyle(binding?.addButton)
    }

    override fun setPlaceViewFragment(place: PlaceView) {
        findNavController().navigate(MainMapFragmentDirections.actionMainMapFragmentToPlaceViewFragment(place))
    }

}