package com.github.ixiaow.exts

import android.content.SharedPreferences

/**
 * key-value 数据存入sharedPreference中
 *
 * @param key key
 * @param t   value 不能为空
 * @param <T> 泛型类型
 */
operator fun <T> SharedPreferences.set(key: String, value: T) {
    this.edit().apply {
        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            is Long -> putLong(key, value)
            else -> putString(key, value?.toString())
        }
    }.apply()
}

/**
 * 根据key取出value
 *
 * @param key          key
 * @param <T>          泛型
 * @return 返回泛型类型的值
</T> */
@Suppress("UNCHECKED_CAST")
operator fun <T> SharedPreferences.get(key: String, defaultValue: T): T {
    return this.all[key] as? T ?: defaultValue
}

