package com.placer.client.screens.places.publish

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.placer.client.Constants
import com.placer.client.R
import com.placer.client.base.BaseFragment
import com.placer.client.databinding.FragmentPlacePublishBinding
import com.placer.client.entity.PlaceView
import com.placer.client.interfaces.TakePhotosPermissions
import com.placer.client.navigation.GalleryTransaction
import com.placer.client.navigation.MainMapTransaction

internal class PlacePublishFragment : BaseFragment(), GalleryTransaction, MainMapTransaction.WithPlace, TakePhotosPermissions {

    override val viewModel: PlacePublishViewModel by activityViewModels()
    private var binding: FragmentPlacePublishBinding? = null
    private var galleryResult: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val photoUri = result.data?.data
            photoUri?.let {
                val imageStream = requireActivity().contentResolver.openInputStream(it)
                imageStream?.let { stream ->
                    viewModel.decodePhotoFromStream(stream)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_place_publish, container, false
        )
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.elevation = Constants.ACTION_BAR_ELEVATION
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.show()
        viewModel.latLng = PlacePublishFragmentArgs.fromBundle(requireArguments()).latLng
        binding?.viewModel = viewModel
        return binding?.root
    }

    override fun onStop() {
        super.onStop()
        refreshData()
    }

    override fun initListeners() {
        binding?.placePhotoView?.setOnClickListener {
            startGallery(galleryResult)
        }
        binding?.publishButton?.setOnClickListener {
            refreshData()
            viewModel.publishPlace()
        }
        viewModel.placePhoto.observe(this, {
            binding?.placePhotoView?.setImageBitmap(it)
        })
        viewModel.placePublished.observe(this, {
            it?.let {
                setMainMapFragment(it)
                viewModel.placePublishedUsed()
            }
        })
    }

    override fun refreshStateChanged(state: Boolean) {
        binding?.baseConstraint?.swipeRefreshLayout?.isRefreshing = state
    }

    override fun loadingStateChanged(state: Boolean) {
        binding?.baseConstraint?.setLoading(state)
    }

    override fun startGallery(launcher: ActivityResultLauncher<Intent>) {
        if (isTakePhotosPermissionsGranted()) {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            launcher.launch(photoPickerIntent)
        }
        else {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), Constants.REQUEST_PHOTOS_PERMISSIONS)
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray) {
        if (requestCode == Constants.REQUEST_PHOTOS_PERMISSIONS) {
            if (grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                startGallery(galleryResult)
            }
        }
    }

    private fun refreshData() {
        binding?.let {
            viewModel.refreshData(
                it.nameField.getText(),
                it.descriptionField.getText(),
                it.publishSwitch.isChecked
            )
        }
    }

    override fun setMainMapFragment(place: PlaceView) {
        requireActivity().startActivity(Intent(requireContext(), PlacePublishSuccessActivity::class.java))
        val bundle = Bundle()
        bundle.putString(Constants.KEY_PLACE_ID, place.id)
        findNavController().navigate(R.id.action_placePublishFragment_to_mainMapFragment, bundle)
    }
}