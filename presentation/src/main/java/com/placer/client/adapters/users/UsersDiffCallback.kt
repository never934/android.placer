package com.placer.client.adapters.users

import androidx.recyclerview.widget.DiffUtil
import com.placer.client.entity.UserView

internal class UsersDiffCallback : DiffUtil.ItemCallback<UserView>() {
    override fun areItemsTheSame(oldItem: UserView, newItem: UserView): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserView, newItem: UserView): Boolean {
        return oldItem == newItem
    }
}