package com.placer.client.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.placer.client.R
import com.placer.client.base.BaseCustomView

class CommentsCountView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseCustomView(context, attrs, defStyleAttr) {
    private lateinit var commentsCountView: TextView

    init {
        init()
    }

    private fun init() {
        this.isClickable = true
        val view = View.inflate(context, R.layout.customview_comments_count, this)
        commentsCountView = view.findViewById(R.id.countView)
    }

    fun setCount(count: Long){
        commentsCountView.text = count.toString()
    }
}