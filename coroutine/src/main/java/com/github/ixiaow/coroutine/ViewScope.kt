package com.github.ixiaow.coroutine

import kotlinx.coroutines.*

const val TAG = "DisposeCoroutine"


var coroutineDebug: Boolean = true

/**
 * 协程接口标志， 如果需要使用viewScope，则需要实现此接口
 */
interface ICoroutineScope {
    fun dispose() {
        viewScope.dispose()
    }
}


/**
 * 协程使用入口
 */
val ICoroutineScope.viewScope: DisposeCoroutineScope by DisposeCoroutineScopeProxy(Dispatchers.Main)


/**
 * 开启一个ui协程
 */
inline fun <T> ICoroutineScope.launchUI(crossinline block: suspend CoroutineScope.() -> T): Job =
    viewScope.launch {
        block()
    }

/**
 * 开启一个io 协程
 */
inline fun <T> ICoroutineScope.launchIO(crossinline block: suspend CoroutineScope.() -> T): Job =
    viewScope.launch {
        withIO {
            block()
        }
    }

/**
 * 开启一个work 协程
 */
inline fun <T> ICoroutineScope.launchWork(crossinline block: suspend CoroutineScope.() -> T): Job =
    viewScope.launch {
        withWork {
            block()
        }
    }

/**
 * 切换到work协程
 */
suspend inline fun <T> CoroutineScope.withWork(noinline block: suspend CoroutineScope.() -> T) {
    withContext(Dispatchers.Default, block)
}

/**
 * 切换到IO协程
 */
suspend inline fun <T> CoroutineScope.withIO(noinline block: suspend CoroutineScope.() -> T) {
    withContext(Dispatchers.IO, block)
}

/**
 * 切换到UI协程
 */
suspend inline fun <T> CoroutineScope.withUI(noinline block: suspend CoroutineScope.() -> T) {
    withContext(Dispatchers.Main, block)
}