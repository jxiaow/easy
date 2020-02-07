package com.github.ixiaow.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * 解析资源布局
 */
fun Context.inflater(
    @LayoutRes id: Int, root: ViewGroup? = null,
    attachToRoot: Boolean = root != null
): View {
    return LayoutInflater.from(this).inflate(id, root, attachToRoot)
}

/**
 * 根据颜色资源[id] 获取颜色值
 */
fun Context.color(@ColorRes id: Int): Int = ContextCompat.getColor(this, id)

fun Context.drawable(@DrawableRes id: Int): Drawable? {
    return ContextCompat.getDrawable(this, id)
}

/**
 * 获取sp的值
 */
fun Context.sp(@DimenRes id: Int): Float = resources.sp(id)

/**
 * 获取dp的值
 */
fun Context.dp(@DimenRes id: Int): Int = resources.dp(id)


/**
 * 自定义布局解析相关属性
 */
@SuppressLint("Recycle")
inline fun Context.withStyledAttributes(
    attributeSet: AttributeSet?,
    attr: IntArray,
    block: TypedArray.() -> Unit
) {
    obtainStyledAttributes(attributeSet, attr).apply(block).recycle()
}