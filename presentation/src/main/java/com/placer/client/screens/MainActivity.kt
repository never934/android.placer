package com.placer.client.screens

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.placer.client.Constants
import com.placer.client.R
import com.placer.client.base.BaseActivity
import com.placer.client.databinding.ActivityMainBinding
import com.placer.client.databinding.NavHeaderBinding
import com.placer.client.screens.city.ChooseCityActivity


internal class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override val viewModel: MainViewModel by viewModels()
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var binding: ActivityMainBinding? = null
    private var navHeaderBinding: NavHeaderBinding? = null
    var chooseCityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            if(result.data?.extras?.get(Constants.CITY_CHOSEN_RESULT_KEY) == true){
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initAll()
    }

    private fun initNavigation(binding: ActivityMainBinding) {
        NavigationUI.setupActionBarWithNavController(this, this.findNavController(R.id.navHostFragment), drawerLayout)
        appBarConfiguration = AppBarConfiguration(this.findNavController(R.id.navHostFragment).graph, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, this.findNavController(R.id.navHostFragment))
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.background_color)))
        binding.navView.setNavigationItemSelectedListener(this)
        viewModel.exitExecute.observe(this, {
            if (it){
                viewModel.exitDone()
                finishAffinity()
            }
        })
    }

    private fun initDrawer(binding: ActivityMainBinding) {
        drawerLayout = binding.drawerLayout
        navHeaderBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.nav_header, binding.navView, false)
        viewModel.profile.observe(this, {
            navHeaderBinding?.let { navHeaderBinding ->
                navHeaderBinding.profile = it
                binding.navView.addHeaderView(navHeaderBinding.root)
            }
            if(it.registrated.not()){
                chooseCityResult.launch(Intent(this, ChooseCityActivity::class.java))
            }
        })
        navHeaderBinding?.constraint?.setOnClickListener {
            openProfileView()
        }
    }

    private fun openProfileView() {
        val bundle = Bundle()
        bundle.putParcelable(Constants.USER_VIEW_KEY, viewModel.profileEntity)
        findNavController(R.id.navHostFragment).navigate(R.id.userViewFragment, bundle)
        drawerLayout.closeDrawers()
    }

    private fun initAll(){
        binding?.let {
            initDrawer(it)
            initNavigation(it)
        }
    }

    fun openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        navHeaderBinding = null
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(this.findNavController(R.id.navHostFragment), drawerLayout)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.exitButton){
            viewModel.exit()
        }
        if (item.itemId == R.id.placesFragment){
            findNavController(R.id.navHostFragment).navigate(R.id.placesFragment)
            drawerLayout.closeDrawers()
        }
        if (item.itemId == R.id.usersTopFragment){
            findNavController(R.id.navHostFragment).navigate(R.id.usersTopFragment)
            drawerLayout.closeDrawers()
        }
        if (item.itemId == R.id.placesTopFragment){
            findNavController(R.id.navHostFragment).navigate(R.id.placesTopFragment)
            drawerLayout.closeDrawers()
        }
        if (item.itemId == R.id.helpFragment){
            findNavController(R.id.navHostFragment).navigate(R.id.helpFirstFragment)
            drawerLayout.closeDrawers()
        }
        return false
    }
}