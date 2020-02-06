package com.github.ixiaow.view

import android.graphics.drawable.Drawable
import android.widget.TextView

fun TextView.compoundDrawablesRelativeWithIntrinsicBounds(
        start: Drawable? = null,
        top: Drawable? = null,
        end: Drawable? = null,
        bottom: Drawable? = null
) {
    setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom)
}