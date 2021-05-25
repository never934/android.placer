package com.placer.client.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.placer.client.R
import com.placer.client.base.BaseCustomView

class BaseConstraintLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseCustomView(context, attrs, defStyleAttr) {

    private lateinit var containerLayout: ConstraintLayout
    lateinit var loadConstraint: ConstraintLayout
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    init {
        init()
    }

    private fun init(){
        this.isClickable = true
        val view = View.inflate(context, R.layout.customview_base_constraint_layout, this)
        containerLayout = view.findViewById(R.id.containerLayout)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        loadConstraint = view.findViewById(R.id.loadConstraint)
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams?) {
        if (childCount != 0) {
            if (id == R.id.loadConstraint){
                super.addView(child, index, params)
            }else{
                containerLayout.addView(child, index, params)
            }
        }else{
            super.addView(child, index, params)
        }
    }
}