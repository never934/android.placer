package com.placer.client.screens

import android.os.Bundle
import android.util.Log
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.placer.client.AppClass
import com.placer.client.R
import com.placer.client.base.BaseActivity
import com.placer.client.databinding.ActivityMainBinding
import com.placer.client.databinding.NavHeaderBinding


class MainActivity : BaseActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var viewModel: MainViewModel
    private var binding: ActivityMainBinding? = null
    private var navHeaderBinding: NavHeaderBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.let {
            drawerLayout = it.drawerLayout
            NavigationUI.setupActionBarWithNavController(this, this.findNavController(R.id.navHostFragment), drawerLayout)
            appBarConfiguration = AppBarConfiguration(this.findNavController(R.id.navHostFragment).graph, drawerLayout)
            NavigationUI.setupWithNavController(it.navView, this.findNavController(R.id.navHostFragment))
            navHeaderBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.nav_header, it.navView, false)
            navHeaderBinding?.let { navHeaderBinding ->
                navHeaderBinding.viewModel = viewModel
                it.navView.addHeaderView(navHeaderBinding.root)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(this.findNavController(R.id.navHostFragment), drawerLayout)
    }

    fun openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        navHeaderBinding = null
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this,
            MainViewModel.Factory(
                AppClass.appInstance.userComponent.loadUserUseCase
            )
        )
            .get(MainViewModel::class.java)
        _viewModel = viewModel
    }
}