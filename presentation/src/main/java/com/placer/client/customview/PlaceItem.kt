package com.placer.client.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.placer.client.R
import com.placer.client.base.BaseCustomView
import com.placer.client.databinding.CustomviewPlaceItemBinding
import com.placer.client.entity.PlaceView

internal class PlaceItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseCustomView(context, attrs, defStyleAttr) {

    private lateinit var binding: CustomviewPlaceItemBinding

    init {
        init()
    }

    private fun init() {
        this.isClickable = true
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.customview_place_item, this, true)
    }

    fun setPlace(place: PlaceView){
        binding.place = place
        binding.invalidateAll()
        binding.executePendingBindings()
    }

    fun getRoot() : ConstraintLayout {
        return binding.root
    }
}
