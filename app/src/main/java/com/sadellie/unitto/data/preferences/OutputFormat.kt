package com.sadellie.unitto.data.preferences

import com.sadellie.unitto.R

/**
 * Output format here means whether or now use engineering notation
 */
object OutputFormat {
    // Never use engineering notation
    const val PLAIN = 0
    // Use format that a lower API returns
    const val ALLOW_ENGINEERING = 1
    // App will try it's best to use engineering notation
    const val FORCE_ENGINEERING = 2
}

/**
 * Available formats. Used in settings
 */
val OUTPUT_FORMAT: Map<Int, Int> by lazy {
    mapOf(
        OutputFormat.PLAIN to R.string.plain,
        OutputFormat.ALLOW_ENGINEERING to R.string.allow_engineering,
        OutputFormat.FORCE_ENGINEERING to R.string.force_engineering,
    )
}
