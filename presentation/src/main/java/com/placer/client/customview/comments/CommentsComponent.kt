package com.placer.client.customview.comments

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.placer.client.R
import com.placer.client.adapters.PlaceCommentsAdapter
import com.placer.client.base.BaseCustomView
import com.placer.client.databinding.CustomviewCommentsComponentBinding
import com.placer.client.entity.PlaceCommentView

internal class CommentsComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseCustomView(context, attrs, defStyleAttr) {

    private lateinit var binding: CustomviewCommentsComponentBinding
    private var adapter = PlaceCommentsAdapter(PlaceCommentsAdapter.PlaceCommentClickListener{})

    init {
        init()
    }

    private fun init() {
        this.isClickable = true
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.customview_comments_component, this, true)
        binding.commentsRecycler.adapter = adapter
    }

    fun setComments(comments: List<PlaceCommentView>?){
        comments?.let {
            binding.loadingConstraint.visibility = View.GONE
            if (comments.isNotEmpty()){
                adapter.submitList(it)
                binding.emptyConstraint.visibility = View.GONE
            }else{
                binding.emptyConstraint.visibility = View.VISIBLE
            }
        }
    }
}