package com.placer.client.screens.places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.placer.client.AppClass
import com.placer.client.R
import com.placer.client.base.BaseFragment
import com.placer.client.databinding.FragmentPlaceViewBinding

class PlaceViewFragment : BaseFragment() {

    private var binding: FragmentPlaceViewBinding? = null
    private lateinit var viewModel: PlaceViewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_place_view, container, false
        )
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.hide()
        binding?.titleView?.text =
            PlaceViewFragmentArgs.fromBundle(requireArguments()).place.name
        binding?.backArrow?.setOnClickListener{ findNavController().navigateUp() }
        return binding?.root
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this,
            PlaceViewViewModel.Factory(
                AppClass.appInstance.placeComponent.loadPlacesUseCase,
                PlaceViewFragmentArgs.fromBundle(requireArguments()).place.id
            )
        )
            .get(PlaceViewViewModel::class.java)
        _viewModel = viewModel
    }

    override fun initListeners() {
        binding?.let { binding ->
            binding.scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                binding.motionLayout.progress = scrollY.toFloat()
                binding.appBarMotionLayout.progress = scrollY.toFloat()
            }
            binding.baseConstraint.swipeRefreshLayout.setOnRefreshListener { viewModel.loadPlace() }
        }
        viewModel.place.observe(this, {
            binding?.place = it
        })
    }

    override fun refreshStateChanged(state: Boolean) {
        binding?.baseConstraint?.swipeRefreshLayout?.isRefreshing = state
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}