package com.placer.client.navigation

import com.placer.client.entity.UserView

internal interface UserViewTransaction {
    fun setUserViewFragment(user: UserView)
}