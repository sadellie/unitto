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

package com.sadellie.unitto.screens.main

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.sadellie.unitto.R

/**
 * Composable for MainScreen top bar title. Changes from "Hello" to "Unitto".
 *
 * @param showAppName When True will show app name, else will show "Hello"
 */
@Composable
fun AnimatedTopBarText(showAppName: Boolean) {
    AnimatedContent(
        targetState = showAppName,
        transitionSpec = {
            (slideInVertically { height -> height } + fadeIn() with
                    slideOutVertically { height -> -height } + fadeOut())
                .using(SizeTransform(clip = false))
        }
    ) {
        Text(
            text = if (showAppName) stringResource(R.string.app_name) else stringResource(R.string.hello_label),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.W600)
        )
    }
}
