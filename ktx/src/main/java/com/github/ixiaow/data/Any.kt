package com.github.ixiaow.data

/**
 * [any]中任何一个等于[Any]则返回true
 */
fun Any.equalsAny(vararg any: Any): Boolean {
    return any.any { this == it }
}
