package com.placer.client.customview.places

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.placer.client.R
import com.placer.client.adapters.places.PlaceClickListener
import com.placer.client.adapters.places.PlacesAdapter
import com.placer.client.base.BaseCustomView
import com.placer.client.databinding.CustomviewCommentsComponentBinding
import com.placer.client.databinding.CustomviewPlacesComponentBinding
import com.placer.client.entity.PlaceView
import com.placer.client.interfaces.OnPlaceChosen

internal class PlacesComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseCustomView(context, attrs, defStyleAttr) {

    private lateinit var binding: CustomviewPlacesComponentBinding
    private lateinit var adapter: PlacesAdapter

    init {
        init()
    }

    private fun init() {
        this.isClickable = true
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.customview_places_component, this, true)
    }

    fun initComponent(callback: OnPlaceChosen){
        adapter = PlacesAdapter(PlaceClickListener { callback.placeChosen(it) })
        binding.placesRecycler.adapter = adapter
    }

    fun setPlaces(places: List<PlaceView>?){
        places?.let {
            binding.loadingConstraint.visibility = View.GONE
            if (places.isNotEmpty()){
                adapter.submitList(it)
                binding.emptyConstraint.visibility = View.GONE
            }else{
                binding.emptyConstraint.visibility = View.VISIBLE
            }
        }
    }
}