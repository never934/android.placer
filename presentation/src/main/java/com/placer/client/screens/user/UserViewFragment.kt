package com.placer.client.screens.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.placer.client.R
import com.placer.client.base.BaseFragment
import com.placer.client.databinding.FragmentUserViewBinding
import com.placer.client.entity.PlaceView
import com.placer.client.interfaces.OnPlaceChosen
import com.placer.client.util.CommonUtils
import kotlinx.android.synthetic.main.fragment_user_view.*

internal class UserViewFragment : BaseFragment(), OnPlaceChosen {

    private var binding: FragmentUserViewBinding? = null
    override val viewModel: UserViewViewModel by viewModels{
        UserViewViewModel.Factory(UserViewFragmentArgs.fromBundle(requireArguments()).user.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_user_view, container, false
        )
        val args = UserViewFragmentArgs.fromBundle(requireArguments())
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.hide()
        binding?.titleView?.text = args.user.nickname ?: args.user.name
        binding?.backArrow?.setOnClickListener{ findNavController().navigateUp() }
        return binding?.root
    }

    override fun initListeners() {
        binding?.let { binding ->
            binding.scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                val motionProgress = CommonUtils.getMotionProgress(30f, scrollY.toFloat())
                binding.appBarMotionLayout.progress = motionProgress
            }
            binding.baseConstraint.swipeRefreshLayout.setOnRefreshListener { viewModel.reload() }
        }
        viewModel.user.observe(this, {
            binding?.user = it
            binding?.fieldsLayout?.visibility = View.VISIBLE
            binding?.executePendingBindings()
        })
        viewModel.isClient.observe(this, {
            if (it){
                binding?.editIcon?.visibility = View.VISIBLE
            }else{
                binding?.editIcon?.visibility = View.GONE
            }
        })
        viewModel.places.observe(this, {
            placesComponent.initComponent(this)
            placesComponent.setPlaces(it)
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

    override fun placeChosen(place: PlaceView) {
        Log.e("chosen", "place chosen ${place.name}")
    }
}