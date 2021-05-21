package com.placer.client.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.placer.client.databinding.CustomviewCommentItemBinding
import com.placer.client.entity.PlaceCommentView

internal class PlaceCommentsAdapter(private val clickListener: PlaceCommentClickListener) : ListAdapter<PlaceCommentView, PlaceCommentsAdapter.ViewHolder>(PlaceCommentsDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(private val binding: CustomviewCommentItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: PlaceCommentClickListener, item: PlaceCommentView) {
            binding.clickListener = clickListener
            binding.placeComment = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CustomviewCommentItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    internal class PlaceCommentsDiffCallback : DiffUtil.ItemCallback<PlaceCommentView>() {
        override fun areItemsTheSame(oldItem: PlaceCommentView, newItem: PlaceCommentView): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PlaceCommentView, newItem: PlaceCommentView): Boolean {
            return oldItem == newItem
        }
    }

    internal class PlaceCommentClickListener(val clickListener: (place: PlaceCommentView) -> Unit) {
        fun onClick(place: PlaceCommentView) = clickListener(place)
    }
}