package com.placer.client

import com.placer.client.util.CommonUtils

object Constants {
    /** custom view **/
    const val SPARSE_STATE_KEY = "SPARSE_STATE_KEY"
    const val SUPER_STATE_KEY = "SUPER_STATE_KEY"

    /** filter groups **/
    const val MY_FILTER_GROUP = 0
    const val ALL_FILTER_GROUP = 1

    /** elevation **/
    const val ACTION_BAR_ELEVATION = 10f

    /** places tabs **/
    const val ALL_TAB_POSITION = 0
    const val MY_TAB_POSITION = 1

    /** view **/
    const val GOOGLE_MAP_ANIMATION_DURATION = 1000
    const val GOOGLE_MAP_INFO_WINDOW_V_ANCHOR = 0.5f
    const val GOOGLE_MAP_ZOOM = 8f
    const val GOOGLE_MAP_CHOOSE_POINT_ZOOM = 15f

    /** google maps **/
    const val DEFAULT_LAT = 37.422
    const val DEFAULT_LNG = -122.08

    /** cities delay **/
    const val CITIES_REQUEST_DELAY = 1000L

    /** permission **/
    const val REQUEST_LOCATION_PERMISSION = 1
    const val REQUEST_PHOTOS_PERMISSIONS = 2

    /** bundle keys **/
    const val CITY_CHOSEN_RESULT_KEY = "CITY_CHOSEN_RESULT_KEY"
    const val USER_VIEW_KEY = "USER_VIEW_KEY"
    const val KEY_PLACE_ID = "KEY_PLACE_ID"

    /** fcm push data **/
    const val FCM_PLACE_ID = "placeId"
}