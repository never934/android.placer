package com.placer.client

import com.placer.client.util.CommonUtils

object Constants {
    /** custom view **/
    const val SPARSE_STATE_KEY = "SPARSE_STATE_KEY"
    const val SUPER_STATE_KEY = "SUPER_STATE_KEY"
    val screenHeightPx = CommonUtils.getScreenHeight()


    // in ms
    const val DEFAULT_ANIMATION_DURATION = 500L

    // filter groups
    const val MY_FILTER_GROUP = 0
    const val ALL_FILTER_GROUP = 1

    // marker focus height
    val MARKER_FOCUS_HEIGHT = CommonUtils.getScreenHeight()

    // places tabs
    const val ALL_TAB_POSITION = 0
    const val MY_TAB_POSITION = 1

    const val GOOGLE_MAP_ANIMATION_DURATION = 1000
    const val GOOGLE_MAP_INFO_WINDOW_V_ANCHOR = 0.5f
}