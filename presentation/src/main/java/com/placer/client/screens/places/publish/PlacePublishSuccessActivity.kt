package com.placer.client.screens.places.publish

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.placer.client.R
import com.placer.client.databinding.ActivityPlacePublishSuccessBinding

class PlacePublishSuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView(this, R.layout.activity_place_publish_success) as ActivityPlacePublishSuccessBinding
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.background_color)))
        supportActionBar?.title = getString(R.string.publish_place_app_bar)
    }
}