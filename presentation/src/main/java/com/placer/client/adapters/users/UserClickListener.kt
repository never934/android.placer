package com.placer.client.adapters.users

import com.placer.client.entity.UserView

internal class UserClickListener(val clickListener: (user: UserView) -> Unit) {
    fun onClick(user: UserView) = clickListener(user)
}