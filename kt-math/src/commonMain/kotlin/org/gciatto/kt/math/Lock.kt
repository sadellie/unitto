package org.gciatto.kt.math

expect inline fun <R> lock(
    any: Any,
    action: () -> R,
): R
