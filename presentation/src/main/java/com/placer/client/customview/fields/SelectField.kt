package com.placer.client.customview.fields

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.placer.client.R
import com.placer.client.base.BaseCustomView
import com.placer.client.databinding.CustomviewEditFieldBinding

class SelectField @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    BaseCustomView(context, attrs, defStyleAttr) {

    private lateinit var binding: CustomviewEditFieldBinding

    init {
        init(attrs, context)
    }

    private fun init(attrs: AttributeSet?, context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.customview_edit_field, this, true)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.SelectField)
        val hint = ta.getString(R.styleable.SelectField_select_field_hint)

        try {
            hint?.let { binding.editTextInputLayout.hint = it }
        } finally {
            ta.recycle()
        }
        binding.editText.isFocusable = false
        binding.editText.isCursorVisible = false
        binding.editText.keyListener = null
    }

    fun getText() : String {
        return binding.editText.text.toString()
    }

    fun setText(text: String?){
        text?.let {
            binding.editText.setText(it, TextView.BufferType.EDITABLE)
        }
    }

    fun getEditText() : EditText {
        return binding.editText
    }
}