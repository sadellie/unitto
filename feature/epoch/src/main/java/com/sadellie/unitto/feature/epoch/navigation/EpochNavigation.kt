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

package com.sadellie.unitto.feature.epoch.navigation

import android.content.Intent
import android.net.Uri
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.sadellie.unitto.feature.epoch.EpochRoute
import com.sadellie.unitto.feature.epoch.R

private const val epochRoute = "epoch_route"

fun NavController.navigateToEpoch() {
    val shortcut = ShortcutInfoCompat
        .Builder(context, epochRoute)
        .setLongLabel(context.getString(R.string.epoch_converter))
        .setLongLabel(context.getString(R.string.epoch_converter))
        .setIntent(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("app://com.sadellie.unitto/$epochRoute")
            )
        )
        .build()
    ShortcutManagerCompat.pushDynamicShortcut(context, shortcut)

    navigate(epochRoute)
}

fun NavGraphBuilder.epochScreen(
    navigateUpAction: () -> Unit
) {
    composable(
        route = epochRoute,
        deepLinks = listOf(
            navDeepLink { uriPattern = "app://com.sadellie.unitto/$epochRoute" }
        )
    ) {
        EpochRoute(
            navigateUpAction = navigateUpAction
        )
    }
}
