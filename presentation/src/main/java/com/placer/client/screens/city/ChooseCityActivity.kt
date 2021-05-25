package com.placer.client.screens.city

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.placer.client.R
import com.placer.client.databinding.ActivityChooseCityBinding

class ChooseCityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityChooseCityBinding = DataBindingUtil.setContentView(this, R.layout.activity_choose_city)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.background_color)))
        supportActionBar?.title = getString(R.string.choose_city_app_bar)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}