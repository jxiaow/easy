package com.github.ixiaow.coroutine

import android.util.Log


private const val TAG = "DisposeCoroutine"


var coroutineDebug: Boolean = true


internal fun debug(msg: String) {
    if (coroutineDebug) {
        Log.d(TAG, msg)
    }
}