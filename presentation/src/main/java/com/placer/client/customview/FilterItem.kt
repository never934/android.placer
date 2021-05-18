package com.placer.client.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.placer.client.R
import com.placer.client.base.BaseCustomView

class FilterItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseCustomView(context, attrs, defStyleAttr) {

    private lateinit var titleView: TextView
    private lateinit var checkmarkView: ImageView

    init {
        init()
    }

    private fun init() {
        this.isClickable = true
        val view = View.inflate(context, R.layout.customview_filter_item, this)
        titleView = view.findViewById(R.id.labelView)
        checkmarkView = view.findViewById(R.id.checkmarkView)
    }

    fun setCheckmark(bool: Boolean){
        if (bool){
            checkmarkView.visibility = VISIBLE
        }else{
            checkmarkView.visibility = INVISIBLE
        }
    }

    fun setLabel(label: String?){
        titleView.text = label
    }

    fun init(bool: Boolean, label: String?){
        setCheckmark(bool)
        setLabel(label)
    }
}