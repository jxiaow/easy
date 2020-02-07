package com.github.ixiaow.view

import android.content.res.Resources
import androidx.annotation.DimenRes

/**
 * 获取dp
 */
fun Resources.dp(@DimenRes id: Int): Int = this.getDimensionPixelOffset(id)

/**
 * 获取sp
 */
fun Resources.sp(@DimenRes id: Int): Float =
    this.getDimensionPixelOffset(id).toFloat() / this.displayMetrics.density
