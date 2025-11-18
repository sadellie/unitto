/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025 Elshan Agaev
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

package com.sadellie.unitto.core.ui

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import co.touchlab.kermit.Logger

private class LinkOpenerImpl(private val context: Context) : LinkOpener {
  override fun launch(url: String) {
    try {
      CustomTabsIntent.Builder().build().launchUrl(context, url.toUri())
    } catch (e: Exception) {
      Logger.e("LinkOpener", e) { "Failed to open link: $url" }
    }
  }
}

@Composable
actual fun rememberLinkOpener(): LinkOpener {
  val context = LocalContext.current
  return remember(context) { LinkOpenerImpl(context) }
}
