package com.placer.client.screens.places

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.placer.client.AppClass
import com.placer.client.R
import com.placer.client.base.BaseFragment
import com.placer.client.customview.comments.CommentField
import com.placer.client.databinding.FragmentPlaceViewBinding
import com.placer.client.util.CommonUtils
import com.placer.client.util.extensions.FragmentExtensions.hideKeyBoard

internal class PlaceViewFragment : BaseFragment(), CommentField.OnSubmitCommentListener {

    private var binding: FragmentPlaceViewBinding? = null
    override val viewModel: PlaceViewViewModel by viewModels{
        PlaceViewViewModel.Factory(PlaceViewFragmentArgs.fromBundle(requireArguments()).place.id)
    }

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

    override fun initListeners() {
        binding?.let { binding ->
            binding.scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                val motionProgress = CommonUtils.getMotionProgress(30f, scrollY.toFloat())
                binding.motionLayout.progress = motionProgress
                binding.appBarMotionLayout.progress = motionProgress
            }
            binding.baseConstraint.swipeRefreshLayout.setOnRefreshListener { viewModel.loadPlace() }
            binding.commentField.initListener(this)
        }
        viewModel.place.observe(this, {
            binding?.place = it
            binding?.placeInfoLayout?.visibility = View.VISIBLE
            binding?.executePendingBindings()
        })
        viewModel.clientIsPlaceAuthor.observe(this, {
            if (it){
                binding?.editIcon?.visibility = View.VISIBLE
                binding?.publishedField?.visibility = View.VISIBLE
            }else{
                binding?.editIcon?.visibility = View.GONE
                binding?.publishedField?.visibility = View.GONE
            }
        })
        viewModel.placeComments.observe(this, {
            binding?.commentsComponent?.setComments(it)
        })
    }

    override fun refreshStateChanged(state: Boolean) {
        binding?.baseConstraint?.swipeRefreshLayout?.isRefreshing = state
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun commentSendClicked(text: String) {
        hideKeyBoard()
        viewModel.sendPlaceComment(text)
    }
}