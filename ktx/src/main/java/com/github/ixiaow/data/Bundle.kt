package com.github.ixiaow.data

import android.os.Bundle

fun bundlesOf(vararg pairs: Pair<String, Any>) = Bundle(pairs.size).apply {
    for ((key, value) in pairs) {
        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            is Double -> putDouble(key, value)
            is ByteArray -> putByteArray(key, value)
            is Byte -> putByte(key, value)
            is Short -> putShort(key, value)
            else -> {
                val valueTye = value.javaClass.canonicalName
                throw  IllegalArgumentException("Illegal value type $valueTye for key \"$key\"")
            }
        }
    }
}