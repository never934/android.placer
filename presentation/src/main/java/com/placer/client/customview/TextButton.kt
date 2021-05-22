package com.placer.client.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.google.android.material.button.MaterialButton
import com.placer.client.R
import com.placer.client.base.BaseCustomView
import com.placer.client.databinding.CustomviewTextButtonBinding

class TextButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseCustomView(context, attrs, defStyleAttr) {
    private lateinit var binding: CustomviewTextButtonBinding

    init {
        init()
    }

    private fun init() {
        this.isClickable = true
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.customview_text_button, this, true)
    }

    fun setText(text: String?) {
        binding.button.text = text
    }

    fun setIcon(icon: Drawable?) {
        binding.button.icon = icon
    }

    fun getButton() : MaterialButton {
        return binding.button
    }
}