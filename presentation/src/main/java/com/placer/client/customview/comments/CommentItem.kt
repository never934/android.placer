package com.placer.client.customview.comments

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.placer.client.R
import com.placer.client.base.BaseCustomView
import com.placer.client.databinding.CustomviewCommentItemBinding
import com.placer.client.entity.PlaceCommentView

internal class CommentItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseCustomView(context, attrs, defStyleAttr) {

    private lateinit var binding: CustomviewCommentItemBinding

    init {
        init()
    }

    private fun init() {
        this.isClickable = true
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.customview_comment_item, this, true)
    }

    fun setComment(placeComment: PlaceCommentView){
        binding.placeComment = placeComment
        binding.invalidateAll()
        binding.executePendingBindings()
    }
}