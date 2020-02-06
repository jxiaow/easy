package com.github.ixiaow.view


import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.graphics.Color
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.github.ixiaow.data.R
import com.gyf.barlibrary.BarHide
import com.gyf.barlibrary.ImmersionBar
import java.lang.ref.WeakReference

/**
 * 功能描述：沉浸式状态栏管理
 * */
class StatusBar private constructor() : LifecycleObserver {
    private var activityRef: WeakReference<Activity>? = null
    private var fragmentRef: WeakReference<Fragment>? = null
    private var owner: LifecycleOwner? = null

    // 创建ImmersionBar对象
    private lateinit var immersionBar: ImmersionBar

    private constructor(activity: Activity) : this() {
        activityRef = WeakReference(activity)
        immersionBar = ImmersionBar.with(activity)
        initStyle()
    }

    private constructor(fragment: Fragment) : this() {
        fragmentRef = WeakReference(fragment)
        immersionBar = ImmersionBar.with(fragment)
        initStyle()
    }

    private fun initStyle() {
        // 设置通用样式
        defaultStyle()
        // 采用 activity / fragment lifeCycle 管理生命周期
        owner = (activityRef?.get() as? LifecycleOwner)?.apply {
            lifecycle.addObserver(this@StatusBar)
            return //如果activity不为空则就不需要再判断fragment了
        }

        owner = (fragmentRef?.get() as? LifecycleOwner)?.apply {
            lifecycle.addObserver(this@StatusBar)
        }
    }


    private fun defaultStyle() {
        immersionBar.apply {
            fitsSystemWindows(true)
            statusBarColor(R.color.status_bar_color)
            statusBarDarkFont(true)
            navigationBarEnable(false) //不操作虚拟导航栏
            keyboardEnable(true, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        }
    }


    companion object {
        /**
         * 创建一个 [StatusBar] 实例 with [Activity]
         */
        @JvmStatic
        fun create(activity: Activity) =
                StatusBar(activity)

        /**
         * 创建一个 [StatusBar] 实例 with [Fragment]
         */
        @JvmStatic
        fun create(fragment: Fragment) =
                StatusBar(fragment)
    }

    /**
     * 设置状态栏的颜色和是否支持白色样式时字体颜色为黑色
     * @param color [Int] 状态栏颜色
     * @param isDarkFont [Boolean] 字体颜色是否支持黑色
     */
    @JvmOverloads
    fun barColor(
            @ColorRes color: Int = R.color.status_bar_color,
            isDarkFont: Boolean = true
    ) = apply {
        immersionBar.apply {
            statusBarColor(color)
            statusBarDarkFont(isDarkFont) //设置字体在白色的时候为黑色
        }
    }

    /**
     * 设置状态栏的颜色和是否支持白色样式时字体颜色为黑色
     * @param color [Int] 状态栏颜色
     * @param isDarkFont [Boolean] 字体颜色是否支持黑色
     */
    @JvmOverloads
    fun statusBarColorInt(
            @ColorInt color: Int = Color.WHITE,
            isDarkFont: Boolean = true
    ) = apply {
        immersionBar.apply {
            statusBarColorInt(color)
            statusBarDarkFont(isDarkFont) //设置字体在白色的时候为黑色
        }
    }

    /**
     * 初始化状态栏，只有调用此方法，一切设置才会生效
     */
    fun init() {
        immersionBar.init()
    }

    /**
     * 是否全屏
     */
    fun fullScreen(fullScreen: Boolean): StatusBar {
        immersionBar.fullScreen(fullScreen)
        if (fullScreen) {
            immersionBar.hideBar(BarHide.FLAG_HIDE_BAR)
        } else {
            immersionBar.hideBar(BarHide.FLAG_SHOW_BAR)
        }
        return this
    }


    /**
     * 利用 activity
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        Log.d("StatusBar", "destroy ->  clear")
        immersionBar.destroy()
        owner?.lifecycle?.removeObserver(this)
        owner = null
        fragmentRef?.clear()
        fragmentRef = null
        activityRef?.clear()
        activityRef = null
    }

    /**
     * 解决状态栏重叠的问题
     */
    fun fitsSystemWindows(fits: Boolean) = apply {
        immersionBar.fitsSystemWindows(fits)
    }

    fun reset() = apply {
        immersionBar.reset()
        defaultStyle()
    }

    /**
     * 状态栏透明
     */
    fun transparentBar() = apply {
        immersionBar.transparentBar()
    }

    /**
     * 状态栏是否支持黑色文字
     */
    fun statusBarDarkFont(isDarkFont: Boolean) = apply {
        immersionBar.statusBarDarkFont(isDarkFont)
    }

    /**
     * 设置键盘
     */
    fun keyboardEnable(enable: Boolean, keyboardMode: Int) = apply {
        immersionBar.keyboardEnable(enable, keyboardMode)
    }


    /**
     * 解决状态栏重叠的问题
     */
    fun titleBarMarginTop(view: View) = apply {
        immersionBar.titleBarMarginTop(view)
    }

    /**
     * 解决状态栏重叠的问题
     */
    fun titleBar(view: View) = apply {
        immersionBar.titleBar(view)
    }

    /**
     * 是否操作虚拟导航栏
     */
    fun navigationBarEnable(navigationBarEnable: Boolean) = apply {
        immersionBar.navigationBarEnable(navigationBarEnable)
    }

    /**
     * 布局图片需要延申到顶部的情况
     */
    fun fitsImageBackground(titleTopMargin: View): StatusBar {
        return this.transparentBar()
                .fitsSystemWindows(false)
                .titleBarMarginTop(titleTopMargin)
                .statusBarDarkFont(false)
                .apply { init() }
    }

    fun immersionBar(block: ImmersionBar.() -> Unit): StatusBar {
        immersionBar.apply(block)
        return this
    }
}

/**
 * 获取状态栏的高度
 */
fun Activity?.getBarHeight(): Int = ImmersionBar.getStatusBarHeight(this)

fun isSupportImmersion() = Build.VERSION.SDK_INT >= 19

/**
 * titleBar 沉浸式状态栏处理， 适用于在布局底部有背景图，并且需要延伸到顶部的情况
 */
fun View.titleBarImmersion(activity: Activity?) {
    if (isSupportImmersion()) {
        setPadding(
                paddingLeft, paddingTop +
                activity.getBarHeight(), paddingRight, paddingBottom
        )
    }
}