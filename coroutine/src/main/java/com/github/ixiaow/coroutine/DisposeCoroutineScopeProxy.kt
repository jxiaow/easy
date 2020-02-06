package com.github.ixiaow.coroutine

import android.arch.lifecycle.LifecycleOwner
import android.util.Log
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.isActive
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KProperty

/**
 * 协程的代理
 */
class DisposeCoroutineScopeProxy(
        override val coroutineContext: CoroutineContext // 协程上下文
) : DisposeCoroutineScope {

    companion object {
        private const val JOB_KEY = "com.github.ixiaow.ICoroutine"
        private val coroutineTags = HashMap<String, DisposeCoroutineScope>()
    }

    override var isDispose: Boolean = false

    override val coroutineId: String = JOB_KEY

    /**
     * 用来缓存已创建的协程
     * [key] 协程id
     * [coroutineScope] 创建的协程
     */
    fun setTagIfAbsent(key: String, coroutineScope: DisposeCoroutineScope): DisposeCoroutineScope {
        var previous: DisposeCoroutineScope?
        synchronized(coroutineTags) {
            previous = coroutineTags[key]
            if (previous == null) {
                coroutineTags[key] = coroutineScope
                debug("create tag: $key")
            }
        }

        return previous ?: coroutineScope
    }

    /**
     * 根据[key] 协程id获取缓存的协程
     */
    fun getTag(key: String): DisposeCoroutineScope? {
        debug("get tag: $key")
        debug("current cache coroutineScopes: $coroutineTags")
        synchronized(coroutineTags) {
            val coroutineScope = coroutineTags[key]
            if (coroutineScope != null) {
                debug("use cache coroutineScope : $key")
            }
            return coroutineScope
        }
    }

    /**
     * 代理类需要实现的方法
     */
    operator fun getValue(any: Any?, property: KProperty<*>): DisposeCoroutineScope {
        val tag = if (any != null) {
            // 协程id
            "$coroutineId$$${any::class.java.simpleName}$$${any.hashCode()}"
        } else {
            "$coroutineId$${property.name}"
        }
        // 获取缓存的协程，如果存在就直接返回
        val coroutineScope = getTag(tag)
        if (coroutineScope != null) {
            return coroutineScope
        }
        // 创建协程
        val coroutineScopeImpl = DisposeCoroutineScopeImpl(
                this,
                SupervisorJob() + coroutineContext, tag
        )
        // 处理activity的生命周期
        if (any is LifecycleOwner) {
            coroutineScopeImpl.wrapperLifecycleOwner(any)
        }
        return setTagIfAbsent(
                tag, coroutineScopeImpl
        )
    }

    override fun dispose() {
        debug("proxy remove disposed coroutine")
        synchronized(coroutineTags) {
            coroutineTags.filterValues {
                it.isDispose ||
                        !it.coroutineContext.isActive
            }.keys.forEach { key ->
                coroutineTags.remove(key)
            }
        }
    }
}

