package com.placer.client.interfaces

interface MainFieldListener {
    fun textInMainFieldChanged(text: String)
    fun mainFieldFocusChanged(hasFocus: Boolean)
    fun showMyPoints()
    fun showAllPoints()
}