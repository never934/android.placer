package com.placer.client.adapters.places

import androidx.recyclerview.widget.DiffUtil
import com.placer.client.entity.PlaceView

internal class PlacesDiffCallback : DiffUtil.ItemCallback<PlaceView>() {
    override fun areItemsTheSame(oldItem: PlaceView, newItem: PlaceView): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PlaceView, newItem: PlaceView): Boolean {
        return oldItem == newItem
    }
}