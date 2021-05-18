package com.placer.client.customview

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.placer.client.R
import com.placer.client.animation.CommonAnimations.expandViewByHeight
import com.placer.client.base.BaseCustomView
import com.placer.client.entity.PlaceView

class PlaceItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseCustomView(context, attrs, defStyleAttr) {

    private lateinit var titleView: TextView
    private lateinit var hintView: TextView
    private lateinit var photoPreview: ShapeableImageView

    init {
        init()
    }

    private fun init() {
        this.isClickable = true
        val view = View.inflate(context, R.layout.customview_place_item, this)
        titleView = view.findViewById(R.id.titleView)
        hintView = view.findViewById(R.id.hintView)
        photoPreview = view.findViewById(R.id.photoPreview)
    }

    internal fun setPlace(place: PlaceView){
        titleView.text = place.name
        hintView.text = place.cityName
        Glide.with(context)
            .load(place.photos.firstOrNull()?.url)
            .error(R.drawable.ic_photo_placeholder)
            .into(photoPreview)
    }
}
