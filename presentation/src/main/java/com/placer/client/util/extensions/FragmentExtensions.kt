package com.placer.client.util.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

object FragmentExtensions {
    fun Fragment.hideKeyBoard() {
        val view = this.activity?.currentFocus
        view?.let { v ->
            val imm = this.activity?.baseContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    fun Fragment.showKeyBoard() {
        (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.apply {
            toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }
    }
}