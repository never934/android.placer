package com.placer.client.base

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import com.placer.client.Constants

abstract class BaseCustomView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        dispatchThawSelfOnly(container)
    }

    override fun onSaveInstanceState(): Parcelable? {
        return Bundle().apply {
            putParcelable(Constants.SUPER_STATE_KEY, super.onSaveInstanceState())
            putSparseParcelableArray(Constants.SPARSE_STATE_KEY, saveChildViewStates())
        }
    }

    fun ViewGroup.saveChildViewStates(): SparseArray<Parcelable> {
        val childViewStates = SparseArray<Parcelable>()
        children.forEach { child -> child.saveHierarchyState(childViewStates) }
        return childViewStates
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var newState = state
        if (newState is Bundle) {
            val childrenState = newState.getSparseParcelableArray<Parcelable>(Constants.SPARSE_STATE_KEY)
            childrenState?.let { restoreChildViewStates(it) }
            newState = newState.getParcelable(Constants.SUPER_STATE_KEY)
        }
        super.onRestoreInstanceState(newState)
    }

    fun ViewGroup.restoreChildViewStates(childViewStates: SparseArray<Parcelable>) {
        children.forEach { child -> child.restoreHierarchyState(childViewStates) }
    }
}