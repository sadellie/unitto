/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022 Elshan Agaev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.sadellie.unitto

import android.content.Context

/**
 * This class is NOT actually using firebase. Used for flavors WITHOUT Firebase dependency.
 */
class FirebaseHelper {

    /**
     * Method that doesn't actually enable/disable analytics.
     */
    @Suppress("UNUSED_PARAMETER")
    fun setAnalyticsCollectionEnabled(context: Context, enable: Boolean) {}

    /**
     * Methods that doesn't actually send exceptions.
     */
    fun recordException(e: Exception) {}
}
