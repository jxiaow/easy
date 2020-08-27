package com.github.ixiaow.view

import android.graphics.drawable.Drawable
import android.widget.TextView

/**
 * 设置左上右下的图标
 */
fun TextView.compoundDrawablesRelativeWithIntrinsicBounds(
    start: Drawable? = null,
    top: Drawable? = null,
    end: Drawable? = null,
    bottom: Drawable? = null
) {
    setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom)
}