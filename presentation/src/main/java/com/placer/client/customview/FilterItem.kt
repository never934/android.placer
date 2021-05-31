package com.placer.client.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.placer.client.R
import com.placer.client.base.BaseCustomView
import com.placer.client.databinding.CustomviewFilterItemBinding

class FilterItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseCustomView(context, attrs, defStyleAttr) {

    private lateinit var binding: CustomviewFilterItemBinding

    init {
        init()
    }

    private fun init() {
        this.isClickable = true
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.customview_filter_item, this, true)
    }

    fun setCheckmark(bool: Boolean){
        if (bool){
            binding.checkmarkView.visibility = VISIBLE
        }else{
            binding.checkmarkView.visibility = INVISIBLE
        }
    }

    fun setLabel(label: String?){
        binding.labelView.text = label
    }

    fun init(bool: Boolean, label: String?){
        setCheckmark(bool)
        setLabel(label)
    }
}