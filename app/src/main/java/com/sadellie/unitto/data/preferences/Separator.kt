package com.sadellie.unitto.data.preferences

import com.sadellie.unitto.R

/**
 * Separators mean symbols that separate fractional part
 */
object Separator {
    const val SPACES = 0
    const val PERIOD = 1
    const val COMMA = 2
}

/**
 * Map of separators that is used in settings
 */
val SEPARATORS: Map<Int, Int> by lazy {
    mapOf(
        Separator.PERIOD to R.string.period,
        Separator.COMMA to R.string.comma,
        Separator.SPACES to R.string.spaces
    )
}
