package com.placer.client.screens.places.update

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.placer.client.Constants
import com.placer.client.R
import com.placer.client.base.BaseFragment
import com.placer.client.databinding.FragmentPlaceUpdateBinding
import com.placer.client.interfaces.DeleteDialog
import com.placer.client.navigation.GalleryTransaction

internal class PlaceUpdateFragment : BaseFragment(), GalleryTransaction, DeleteDialog, DeleteDialog.OnDelete {

    override val viewModel: PlaceUpdateViewModel by viewModels{
        PlaceUpdateViewModel.Factory(PlaceUpdateFragmentArgs.fromBundle(requireArguments()).placeId)
    }
    private var binding: FragmentPlaceUpdateBinding? = null
    private var galleryResult: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val photoUri = result.data?.data
            photoUri?.let {
                val imageStream = requireActivity().contentResolver.openInputStream(it)
                imageStream?.let { stream ->
                    viewModel.uploadPlacePhoto(stream)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_place_update, container, false
        )
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.elevation = Constants.ACTION_BAR_ELEVATION
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.show()
        return binding?.root
    }

    override fun initListeners() {
        viewModel.place.observe(this, {
            binding?.place = it
        })
        viewModel.placeDeleteExecuted.observe(this, {
            if (it){
                findNavController().navigateUp()
                findNavController().navigateUp()
                viewModel.deletePlaceProcessed()
            }
        })
        binding?.let { binding ->
            binding.deleteButton.setOnClickListener { showDeleteDialog() }
            binding.placePhotoView.setOnClickListener { startGallery(galleryResult) }
            binding.saveButton.setOnClickListener {
                viewModel.updatePlace(
                    name = binding.nameField.getText(),
                    description = binding.descriptionField.getText(),
                    published = binding.publishSwitch.isChecked
                )
            }
        }
    }

    override fun refreshStateChanged(state: Boolean) {
        binding?.baseConstraint?.swipeRefreshLayout?.isRefreshing = state
    }

    override fun loadingStateChanged(state: Boolean) {
        binding?.baseConstraint?.setLoading(state)
    }

    override fun startGallery(launcher: ActivityResultLauncher<Intent>) {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        launcher.launch(photoPickerIntent)
    }

    override fun showDeleteDialog() {
        showDeleteDialog(requireActivity(), this)
    }

    override fun deleteFromDialogClicked() {
        viewModel.deletePlace()
    }
}