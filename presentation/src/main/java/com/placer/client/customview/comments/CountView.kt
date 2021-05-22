package com.placer.client.customview.comments

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.placer.client.R
import com.placer.client.base.BaseCustomView

class CountView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseCustomView(context, attrs, defStyleAttr) {
    private lateinit var commentsCountView: TextView
    private lateinit var iconView: ImageView

    init {
        init()
    }

    private fun init() {
        this.isClickable = true
        val view = View.inflate(context, R.layout.customview_count, this)
        commentsCountView = view.findViewById(R.id.countView)
        iconView = view.findViewById(R.id.iconView)
    }

    fun setCount(count: Long){
        commentsCountView.text = count.toString()
    }

    fun setIcon(drawable: Drawable?){
        iconView.setImageDrawable(drawable)
    }
}