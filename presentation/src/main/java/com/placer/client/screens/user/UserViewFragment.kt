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
import com.placer.client.Constants
import com.placer.client.R
import com.placer.client.base.BaseFragment
import com.placer.client.databinding.FragmentUserViewBinding
import com.placer.client.entity.PlaceView
import com.placer.client.entity.UserView
import com.placer.client.interfaces.OnPlaceChosen
import com.placer.client.navigation.EditProfileTransaction
import com.placer.client.util.CommonUtils
import kotlinx.android.synthetic.main.fragment_user_view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

internal class UserViewFragment : BaseFragment(), OnPlaceChosen, EditProfileTransaction {

    private var binding: FragmentUserViewBinding? = null
    override val viewModel: UserViewViewModel by viewModels{
        requireArguments().getParcelable<UserView>(Constants.USER_VIEW_KEY)?.let {
            UserViewViewModel.Factory(it.id)
        } ?: run {
            UserViewViewModel.Factory(UserViewFragmentArgs.fromBundle(requireArguments()).user?.id ?: "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_user_view, container, false
        )
        if (requireArguments().getParcelable<UserView>(Constants.USER_VIEW_KEY) != null) {
            val user = requireArguments().getParcelable<UserView>(Constants.USER_VIEW_KEY)
            binding?.titleView?.text = user?.nickname ?: user?.name
        }else{
            val args = UserViewFragmentArgs.fromBundle(requireArguments())
            binding?.titleView?.text = args.user?.nickname ?: args.user?.name
        }
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.hide()
        binding?.backArrow?.setOnClickListener{ findNavController().navigateUp() }
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.reload()
    }

    override fun initListeners() {
        placesComponent.initComponent(this)
        binding?.let { binding ->
            binding.scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
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
                binding?.editIcon?.setOnClickListener { setEditProfileFragment() }
            }else{
                binding?.editIcon?.visibility = View.GONE
            }
        })
        viewModel.places.observe(this, {
            placesComponent.setPlaces(it)
        })
    }

    override fun refreshStateChanged(state: Boolean) {
        binding?.baseConstraint?.swipeRefreshLayout?.isRefreshing = state
    }

    override fun loadingStateChanged(state: Boolean) {
        binding?.baseConstraint?.setLoading(state)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun placeChosen(place: PlaceView) {
        findNavController().navigate(UserViewFragmentDirections.actionUserViewFragmentToPlaceViewFragment(place))
    }

    override fun setEditProfileFragment() {
        findNavController().navigate(UserViewFragmentDirections.actionUserViewFragmentToProfileEditFragment())
    }
}