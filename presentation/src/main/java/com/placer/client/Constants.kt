package com.placer.client

import com.placer.client.util.CommonUtils

object Constants {
    /** custom view **/
    const val SPARSE_STATE_KEY = "SPARSE_STATE_KEY"
    const val SUPER_STATE_KEY = "SUPER_STATE_KEY"


    // in ms
    const val DEFAULT_ANIMATION_DURATION = 500L

    // filter groups
    const val MY_FILTER_GROUP = 0
    const val ALL_FILTER_GROUP = 1

    // marker focus height
    val MARKER_FOCUS_HEIGHT = CommonUtils.getScreenHeight()
}