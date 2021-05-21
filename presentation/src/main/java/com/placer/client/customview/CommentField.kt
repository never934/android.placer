package com.placer.client.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.cardview.widget.CardView
import com.placer.client.R
import com.placer.client.base.BaseCustomView

class CommentField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseCustomView(context, attrs, defStyleAttr) {

    private lateinit var commentCard: CardView

    init {
        init()
    }

    private fun init() {
        this.isClickable = true
        val view = View.inflate(context, R.layout.customview_comment_field, this)
        commentCard = view.findViewById(R.id.commentCard)
        commentCard.setBackgroundResource(R.drawable.comment_field_card_background)
    }
}