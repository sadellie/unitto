package org.gciatto.kt.math

actual inline fun <R> lock(
    any: Any,
    action: () -> R,
): R = synchronized(any, action)
