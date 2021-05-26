package com.placer.client.screens.user.profile

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
import com.placer.client.databinding.FragmentProfileEditBinding
import com.placer.client.navigation.ChooseCityTransaction
import com.placer.client.navigation.GalleryTransaction

internal class ProfileEditFragment : BaseFragment(), ChooseCityTransaction, GalleryTransaction {

    private var binding: FragmentProfileEditBinding? = null
    override val viewModel: ProfileEditViewModel by viewModels()
    private var galleryResult: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val photoUri = result.data?.data
            photoUri?.let {
                val imageStream = requireActivity().contentResolver.openInputStream(it)
                imageStream?.let {
                    viewModel.updateAvatar(imageStream)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_profile_edit, container, false
        )
        (requireActivity() as AppCompatActivity).supportActionBar?.elevation = Constants.ACTION_BAR_ELEVATION
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.show()
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadProfile()
    }

    override fun initListeners() {
        binding?.let { binding ->
            binding.baseConstraint.swipeRefreshLayout.setOnRefreshListener { viewModel.loadProfile() }
            binding.saveButton.setOnClickListener { viewModel.updateProfile(binding.nameField.getText(), binding.nicknameField.getText()) }
            binding.avatarView.setOnClickListener { startGallery(galleryResult) }
            binding.cityField.getEditText().setOnClickListener { setChooseCityFragment() }
        }
        viewModel.profile.observe(this, {
            binding?.user = it
            binding?.executePendingBindings()
        })
    }

    override fun refreshStateChanged(state: Boolean) {
        binding?.baseConstraint?.swipeRefreshLayout?.isRefreshing = state
    }

    override fun loadingStateChanged(state: Int) {
        binding?.baseConstraint?.loadConstraint?.visibility = state
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun setChooseCityFragment() {
        findNavController().navigate(ProfileEditFragmentDirections.actionProfileEditFragmentToChooseCityFragment())
    }

    override fun startGallery(launcher: ActivityResultLauncher<Intent>) {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        launcher.launch(photoPickerIntent)
    }

}