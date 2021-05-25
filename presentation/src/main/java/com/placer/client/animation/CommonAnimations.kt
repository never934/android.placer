package com.placer.client.animation

import android.R.attr
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View


object CommonAnimations {
    fun View.playTranslationXAnimation(translation: Float){
        val objectAnimator = ObjectAnimator.ofFloat(this, "translationX", translation)
        objectAnimator.start()
    }

    fun View.playScaleXAnimation(translation: Float){
        val objectAnimator = ObjectAnimator.ofFloat(this, "scaleX", translation)
        objectAnimator.start()
    }

    fun View.playXAnimation(x: Float){
        ObjectAnimator.ofFloat(this, "x", x).start()
    }

    fun View.playYAnimation(y: Float){
        ObjectAnimator.ofFloat(this, "y", y).start()
    }

    fun View.expandViewByWidth(width: Int, newWidth: Int) : ValueAnimator {
        val animator = ValueAnimator.ofInt(width, newWidth)
        animator.addUpdateListener {
            val params = this.layoutParams
            params.width = it.animatedValue as Int
            this.layoutParams = params
        }
        return animator
    }

    fun View.expandViewByHeight(height: Int, newHeight: Int) : ValueAnimator {
        val animator = ValueAnimator.ofInt(height, newHeight)
        animator.addUpdateListener {
            val params = this.layoutParams
            params.height = it.animatedValue as Int
            this.layoutParams = params
        }
        return animator
    }

}