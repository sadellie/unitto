package com.sadellie.unitto.data.preferences

import com.sadellie.unitto.R

/**
 * Current maximum scale that will be used in app. Used in various place in code
 */
const val MAX_PRECISION: Int = 1_000

/**
 * Currently available scale options
 */
val PRECISIONS: Map<Int, Int> by lazy {
    mapOf(
        0 to R.string.precision_zero,
        1 to R.string.precision_one,
        2 to R.string.precision_two,
        3 to R.string.precision_three,
        4 to R.string.precision_four,
        5 to R.string.precision_five,
        6 to R.string.precision_six,
        7 to R.string.precision_seven,
        8 to R.string.precision_eight,
        9 to R.string.precision_nine,
        10 to R.string.precision_ten,
        11 to R.string.precision_eleven,
        12 to R.string.precision_twelve,
        13 to R.string.precision_thirteen,
        14 to R.string.precision_fourteen,
        15 to R.string.precision_fifteen,
        MAX_PRECISION to R.string.max_precision
    )
}
