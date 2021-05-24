package com.placer.client.adapters.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.placer.client.databinding.CustomviewUserItemBinding
import com.placer.client.entity.UserView

internal class UsersAdapter(private val clickListener: UserClickListener) : ListAdapter<UserView, UsersAdapter.ViewHolder>(UsersDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(private val binding: CustomviewUserItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: UserClickListener, item: UserView) {
            binding.clickListener = clickListener
            binding.user = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CustomviewUserItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}