package com.github.ixiaow.view

import android.graphics.drawable.Drawable
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * 设置view是否为gone
 */
var View.gone: Boolean
    set(value) {
        visibility = if (value) View.GONE else View.VISIBLE
    }
    get() = visibility == View.GONE

/**
 * 设置view是否为invisible
 */
var View.invisible: Boolean
    set(value) {
        visibility = if (value) View.INVISIBLE else View.VISIBLE
    }
    get() = visibility == View.INVISIBLE


/**
 * 解析资源布局
 */
fun View.inflater(
        @LayoutRes id: Int, root: ViewGroup?,
        attachToRoot: Boolean = root != null
): View {
    return LayoutInflater.from(context).inflate(id, root, attachToRoot)
}

/**
 * 设置layout params
 */
inline fun <reified T : ViewGroup.LayoutParams> View.paramsOf(block: T.() -> Unit) {
    layoutParams = (layoutParams as T).apply(block)
}


/**
 * (x,y)是否在view的区域内
 * @param view 需要判断的view
 * @param x x坐标点
 * @param y y坐标点
 * @return true or false
 */
fun View?.isTouchPointInView(x: Int, y: Int): Boolean {
    if (this == null) {
        return false
    }
    val location = IntArray(2)
    getLocationOnScreen(location)
    val left = location[0]
    val top = location[1]
    val right = left + measuredWidth;
    val bottom = top + measuredHeight;
    return y in top..bottom && x >= left && x <= right
}

