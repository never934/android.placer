package com.placer.client.customview.comments

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import com.placer.client.R
import com.placer.client.base.BaseCustomView
import com.placer.client.databinding.CustomviewCommentFieldBinding

class CommentField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseCustomView(context, attrs, defStyleAttr) {

    private lateinit var binding: CustomviewCommentFieldBinding

    init {
        init()
    }

    private fun init() {
        this.isClickable = true
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.customview_comment_field, this, true)
        binding.commentCard.setBackgroundResource(R.drawable.comment_field_card_background)
    }

    fun initListener(listener: OnSubmitCommentListener) {
        binding.sendButton.setOnClickListener {
            binding.editField
            if (binding.editField.text.isNotEmpty()){
                listener.commentSendClicked(binding.editField.text.toString())
                binding.editField.text.clear()
                binding.editField.clearFocus()
            }else{
                Toast.makeText(context, context.getString(R.string.common_err_empty), Toast.LENGTH_SHORT).show()
            }
        }
    }

    interface OnSubmitCommentListener{
        fun commentSendClicked(text: String)
    }
}