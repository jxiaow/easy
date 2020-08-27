package com.github.ixiaow.data

import androidx.collection.ArrayMap

/**
 * 快速创建arrayMap
 */
fun <K, V> arrayMapOf(vararg pairs: Pair<K, V>) = ArrayMap<K, V>(pairs.size).apply {
    for (pair in pairs) {
        put(pair.first, pair.second)
    }
}