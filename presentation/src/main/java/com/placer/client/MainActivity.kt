package com.placer.client

import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.placer.client.base.BaseActivity
import com.placer.client.databinding.ActivityMainBinding


class MainActivity : BaseActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout
        NavigationUI.setupActionBarWithNavController(this, this.findNavController(R.id.navHostFragment), drawerLayout)
        appBarConfiguration = AppBarConfiguration(this.findNavController(R.id.navHostFragment).graph, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, this.findNavController(R.id.navHostFragment))
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(this.findNavController(R.id.navHostFragment), drawerLayout)
    }

    fun openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    override fun initViewModel() {}
}