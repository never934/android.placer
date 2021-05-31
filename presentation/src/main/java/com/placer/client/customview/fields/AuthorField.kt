package com.placer.client.customview.fields

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.placer.client.R
import com.placer.client.base.BaseCustomView
import com.placer.client.databinding.CustomviewAuthorFieldBinding
import com.placer.client.entity.UserView

internal class AuthorField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseCustomView(context, attrs, defStyleAttr) {

    private lateinit var binding: CustomviewAuthorFieldBinding

    init {
        init()
    }

    private fun init() {
        this.isClickable = true
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.customview_author_field, this, true)
    }

    fun setUser(user: UserView?) {
        user?.let {
            binding.user = user
            binding.executePendingBindings()
        }
    }

    fun getField() : ConstraintLayout {
        return binding.constraint
    }
}