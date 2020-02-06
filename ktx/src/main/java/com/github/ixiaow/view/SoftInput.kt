package com.github.ixiaow.view

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * 显示软键盘和隐藏软键盘
 */
fun EditText?.softInput(hide: Boolean) {
    if (this == null) {
        return
    }
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        ?: return

    if (hide) {
        imm.hideSoftInputFromWindow(windowToken, 0)
        return
    }
    requestFocus()
    postDelayed({ imm.showSoftInput(this, 0) }, 100)
}
