package com.sadellie.unitto.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.sadellie.unitto.data.KEY_COMMA
import com.sadellie.unitto.data.KEY_DOT
import com.sadellie.unitto.data.KEY_E
import com.sadellie.unitto.data.preferences.Separator
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.max


object Formatter {
    private var nf: NumberFormat = NumberFormat.getInstance(Locale.GERMANY)

    /**
     * Currently used symbol to separate fractional part
     */
    var fractional = KEY_COMMA

    /**
     * Change current separator
     *
     * @param separator [Separator] to change to
     */
    fun setSeparator(separator: Int) {
        nf = when (separator) {
            Separator.PERIOD -> NumberFormat.getInstance(Locale.GERMANY)
            Separator.COMMA -> NumberFormat.getInstance(Locale.US)
            // SPACE BASICALLY
            else -> NumberFormat.getInstance(Locale.FRANCE)
        }
        fractional = if (separator == Separator.PERIOD) KEY_COMMA else KEY_DOT
    }

    /**
     * Custom formatter function which work with big decimals and with strings ending with a dot.
     * Also doesn't lose any precision
     * @param[input] The string we want to format. Will be split with dot symbol
     */
    fun format(input: String): String {
        // NOTE: We receive input like 1234 or 1234. or 1234.5
        // NOTICE DOTS, not COMMAS

        // For engineering string we only replace decimal separator
        if (input.contains(KEY_E)) return input.replace(KEY_DOT, fractional)

        // Stupid Huawei catching impossible bugs, stupid workaround
        return try {
            var result = String()
            // Formatting everything before fractional part
            result += nf.format(input.substringBefore(KEY_DOT).toBigInteger())
            // Now we add the part after dot
            if (input.contains(KEY_DOT)) {
                result += fractional + input.substringAfter(KEY_DOT)
            }
            result
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            input
        }
    }
}

/**
 * Sets the minimum scale that is required to get first non zero value in fractional part
 *
 * @param[prefScale] Is the preferred scale, the one which will be compared against
 */
fun BigDecimal.setMinimumRequiredScale(prefScale: Int): BigDecimal {
    /* Here we are getting the amount of zeros in fractional part before non zero value
        * For example, for 0.00000123456 we need the length of 00000
        * Next we add one to get the position of the first non zero value
        *
        * Also, this block is only for VERY small numbers
        * */
    return this.setScale(
        max(
            prefScale,
            if (this.abs() < BigDecimal.ONE) {
                // https://stackoverflow.com/a/46136593
                -floor(log10(this.abs().remainder(BigDecimal.ONE).toDouble())).toInt()
            } else {
                0
            }
        ),
        RoundingMode.HALF_EVEN
    )
}

/**
 * Open given link in browser
 */
fun openLink(mContext: Context, url: String) {
    mContext.startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)))
}


/**
 * Compute Levenshtein Distance. Doesn't really matter which string goes first
 *
 * @param stringB Second string
 * @return The amount of changes that are needed to transform one string into another
 */
fun CharSequence.lev(stringB: String): Int {
    // Skipping computation for this cases
    if (this == stringB) return 0
    if (this.isEmpty()) return stringB.length
    // This case is basically unreal in this app, because stringB is a unit name and are never empty
    if (stringB.isEmpty()) return this.length

    var cost = IntArray(this.length + 1) { it }
    var newCost = IntArray(this.length + 1)

    for (i in 1..stringB.length) {
        // basically shifting this to the right by 1 each time
        newCost[0] = i

        for (j in 1..this.length) {
            newCost[j] = minOf(
                // Adding 1 if they don't match, i.e. need to replace
                cost[j - 1] + if (this[j - 1] == stringB[i - 1]) 0 else 1,
                // Insert
                cost[j] + 1,
                // Delete
                newCost[j - 1] + 1
            )
        }

        // Swapping costs
        cost = newCost.also { newCost = cost }
    }

    return cost[this.length]
}
