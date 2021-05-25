package com.placer.client.customview.fields

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.placer.client.R
import com.placer.client.base.BaseCustomView
import com.placer.client.databinding.CustomviewViewFieldBinding

internal class ViewField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseCustomView(context, attrs, defStyleAttr) {

    private lateinit var binding: CustomviewViewFieldBinding

    init {
        init()
    }

    private fun init() {
        this.isClickable = true
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.customview_view_field, this, true)
    }

    fun setHint(hint: String?){
        hint?.let {
            binding.hintView.text = hint
        } ?: run {
            binding.hintView.text = context.getString(R.string.null_representation)
        }
    }

    fun setContentColor(color: Int?){
        color?.let {
            binding.contentView.setTextColor(it)
        }
    }

    fun setIcon(icon: Drawable?){
        binding.iconView.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.color_primary_light))
        binding.iconView.setImageDrawable(icon)
    }

    fun setContent(content: String?){
        if (content.isNullOrEmpty()){
            binding.contentView.text = context.getString(R.string.null_representation)
        }else{
            binding.contentView.text = content
        }
    }
}
