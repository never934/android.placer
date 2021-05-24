package com.placer.client.adapters.places

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.placer.client.databinding.CustomviewPlaceItemBinding
import com.placer.client.entity.PlaceView

internal class PlacesAdapter(private val clickListener: PlaceClickListener) : ListAdapter<PlaceView, PlacesAdapter.ViewHolder>(PlacesDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(private val binding: CustomviewPlaceItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: PlaceClickListener, item: PlaceView) {
            binding.clickListener = clickListener
            binding.place = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CustomviewPlaceItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}