package com.placer.client.util.binding

import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.switchmaterial.SwitchMaterial
import com.placer.client.customview.fields.AuthorField
import com.placer.client.customview.fields.EditField
import com.placer.client.customview.fields.SelectField
import com.placer.client.customview.fields.ViewField
import com.placer.client.entity.UserView

@BindingAdapter("content")
internal fun bindContent(view: ViewField, content: String?) {
    view.setContent(content)
}

@BindingAdapter("icon")
internal fun bindIcon(view: ViewField, drawable: Drawable?) {
    view.setIcon(drawable)
}

@BindingAdapter("hint")
internal fun bindHint(view: ViewField, hint: String?) {
    view.setHint(hint)
}

@BindingAdapter("contentColor")
internal fun contentColor(view: ViewField, color: Int?) {
    view.setContentColor(color)
}

@BindingAdapter("text")
internal fun bindText(view: EditField, text: String?) {
    view.setText(text)
}

@BindingAdapter("text")
internal fun bindText(view: SelectField, text: String?) {
    view.setText(text)
}

@BindingAdapter("toggleValue")
internal fun bindToggleValue(view: SwitchMaterial, state: Boolean?) {
    state?.let {
        view.isChecked = state
    }
}

@BindingAdapter("author")
internal fun bindAuthor(view: AuthorField, author: UserView?) {
    view.setUser(author)
}