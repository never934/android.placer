package com.placer.client.util

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.placer.client.R
import com.placer.client.customview.comments.CountView
import com.placer.domain.entity.place.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class InfoWindowAdapter(private val myContext: FragmentActivity, private val places: List<Place>) : GoogleMap.InfoWindowAdapter {
    private val view: View

    override fun getInfoContents(marker: Marker?): View? {
        return null
    }

    override fun getInfoWindow(marker: Marker): View {
        val titleUi = view.findViewById(R.id.nameView) as TextView
        val placePhotoView = view.findViewById(R.id.placePhotoView) as ImageView
        val commentsCountView = view.findViewById(R.id.commentsCountView) as CountView
        val placeId: String = marker.title
        val place = places.first{ it.id == placeId }

        titleUi.typeface = Typeface.DEFAULT_BOLD
        titleUi.text = place.name
        commentsCountView.setCount(place.commentsCount)
        if (place.photos.isNotEmpty()){
            runBlocking {
                withContext(Dispatchers.IO) {
                    placePhotoView.setImageDrawable(CommonUtils.loadDrawableFromUrl(placePhotoView.context, place.photos.first().url))
                }
            }
        }else{
            placePhotoView.setImageDrawable(
                ContextCompat.getDrawable(myContext, R.drawable.ic_photo_placeholder)
            )
        }
        return view
    }

    init {
        val inflater =
            myContext.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(
            R.layout.customview_place_snippet,
            null
        )
    }
}