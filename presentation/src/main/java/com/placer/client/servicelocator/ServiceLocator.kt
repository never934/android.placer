package com.placer.client.servicelocator

object ServiceLocator {

    private lateinit var instanceVar: DependencyInstance
    val instance: DependencyInstance
    get() = instanceVar

    fun setDependencyInstance(instance: DependencyInstance) {
        instanceVar = instance
    }
}