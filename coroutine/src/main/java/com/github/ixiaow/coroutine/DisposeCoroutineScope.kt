package com.github.ixiaow.coroutine

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext

/**
 * 可销毁的协程
 */
interface DisposeCoroutineScope : CoroutineScope {
    /**
     * 当前协程的唯一标识
     */
    val coroutineId: String

    /**
     * 当前协程是否被销毁
     */
    var isDispose: Boolean

    /**
     * 销毁协程
     */
    fun dispose()
}

/**
 * 协程实现类
 */
class DisposeCoroutineScopeImpl(
    private val parent: DisposeCoroutineScope, // 父协程
    override val coroutineContext: CoroutineContext, // 协程上下文
    override val coroutineId: String, // 协程id
    override var isDispose: Boolean = false // 协程是否已经销毁
) : DisposeCoroutineScope, LifecycleObserver {
    /**
     * 生命周期感知
     */
    private var weakLifecycleOwner: WeakReference<LifecycleOwner>? = null

    /**
     * 协程的销毁，在应用中我们通常是调用此方法来销毁
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun dispose() {
        try {
            // 取消当前协程
            coroutineContext.cancel()
            debug("$coroutineId dispose")
            isDispose = true
            // 调用父协程的销毁方法
            parent.dispose()
        } catch (e: Exception) {
            debug("$coroutineId dispose  exception: ${e.message}")
        } finally {
            // 移除生命周期感知
            weakLifecycleOwner?.let {
                it.get()?.lifecycle?.removeObserver(this)
                it.clear()
            }
        }
    }

    fun wrapperLifecycleOwner(owner: LifecycleOwner) {
        weakLifecycleOwner = WeakReference(owner)
        owner.lifecycle.addObserver(this)
    }

    private fun debug(msg: String) {
        if (coroutineDebug) {
            Log.d(TAG, msg)
        }
    }
}