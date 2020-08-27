package com.github.ixiaow.view

import android.content.res.Resources
import android.util.TypedValue
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

/**
 * 将float类型的dp转为px, 注意此方法是采用的是系统的[Resources],
 * 如果自定义了[Resources]或修改了像素密度则不能使用此方法
 */
val Float.dp: Int
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            Resources.getSystem().displayMetrics
        ).toInt()
    }

/**
 * 将int类型的dp转为px, 注意此方法是采用的是系统的[Resources],
 * 如果自定义了[Resources]或修改了像素密度则不能使用此方法
 */
val Int.dp: Float
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        )
    }