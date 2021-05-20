package com.placer.client.interfaces

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.placer.client.R

interface PlacerFabStyle {
    fun initFabStyle(vararg fab: ExtendedFloatingActionButton?){
        fab.forEach {
            it?.backgroundTintList = null
            it?.setBackgroundResource(R.drawable.fab_gradient)
        }
    }
    fun initFabStyle()
}