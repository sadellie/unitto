/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2024 Elshan Agaev
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

package com.sadellie.unitto.core.common

import android.content.ActivityNotFoundException
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri

/** Open given link in browser */
fun openLink(mContext: Context, url: String) =
  try {
    CustomTabsIntent.Builder().build().launchUrl(mContext, url.toUri())
  } catch (e: ActivityNotFoundException) {
    Log.d("UIUtils", "Failed to open link", e)
    showToast(mContext, mContext.getString(R.string.common_error))
  }

fun showToast(mContext: Context, text: String, duration: Int = Toast.LENGTH_SHORT) =
  Toast.makeText(mContext, text, duration).show()
