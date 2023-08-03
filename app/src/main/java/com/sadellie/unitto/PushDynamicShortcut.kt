/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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
import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// I think it makes sense to run in coroutine
suspend fun Context.pushDynamicShortcut(
    route: String,
    @StringRes shortLabel: Int,
    @StringRes longLabel: Int,
    @DrawableRes drawable: Int,
) = withContext(Dispatchers.IO) {
    // Low chance that we WILL push the shortcut
    if ((0..10).random() != 0) return@withContext

    val context = this@pushDynamicShortcut

    val shortcut = ShortcutInfoCompat.Builder(context, route)
        .setShortLabel(getString(shortLabel))
        .setLongLabel(getString(longLabel))
        .setIcon(IconCompat.createWithResource(context, drawable))
        .setIntent(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("app://com.sadellie.unitto/$route"),
                context,
                context.javaClass
            )
        )
        .build()

    ShortcutManagerCompat.pushDynamicShortcut(context, shortcut)
}
