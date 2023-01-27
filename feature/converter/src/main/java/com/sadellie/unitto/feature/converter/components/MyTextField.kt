/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2022 Elshan Agaev
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

package com.sadellie.unitto.feature.converter.components

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.with
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.ui.theme.NumbersTextStyleDisplayLarge
import com.sadellie.unitto.core.ui.theme.NumbersTextStyleDisplayMedium
import com.sadellie.unitto.core.ui.R

/**
 * Component for input and output
 *
 * @param modifier Modifier that is applied to [LazyRow].
 * @param primaryText Primary text to show (input/output).
 * @param secondaryText Secondary text to show (input, calculated result).
 * @param helperText Helper text below current text (short unit name).
 * @param textToCopy Text that will be copied to clipboard when long-clicking.
 */
@Composable
internal fun MyTextField(
    modifier: Modifier,
    primaryText: @Composable () -> String,
    secondaryText: String?,
    helperText: String,
    textToCopy: String,
) {
    val clipboardManager = LocalClipboardManager.current
    val mc = LocalContext.current
    val textToShow: String = primaryText()
    val copiedText: String =
        stringResource(R.string.copied, textToCopy)

    Column(
        modifier = Modifier
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = {},
                onLongClick = {
                    clipboardManager.setText(AnnotatedString(secondaryText ?: textToShow))
                    Toast
                        .makeText(mc, copiedText, Toast.LENGTH_SHORT)
                        .show()
                }
            )
    ) {
        LazyRow(
            modifier = modifier
                .wrapContentHeight(),
            reverseLayout = true,
            horizontalArrangement = Arrangement.End,
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            item {
                AnimatedContent(
                    targetState = textToShow,
                    transitionSpec = {
                        // Enter animation
                        (expandHorizontally(clip = false, expandFrom = Alignment.Start) + fadeIn()
                                // Exit animation
                                with fadeOut())
                            .using(SizeTransform(clip = false))
                    }
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        // Quick fix to prevent the UI from crashing
                        text = it.take(1000),
                        textAlign = TextAlign.End,
                        softWrap = false,
                        style = NumbersTextStyleDisplayLarge
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = !secondaryText.isNullOrEmpty(),
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            LazyRow(
                modifier = modifier
                    .wrapContentHeight(),
                reverseLayout = true,
                horizontalArrangement = Arrangement.End,
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                item {
                    AnimatedContent(
                        targetState = secondaryText,
                        transitionSpec = {
                            // Enter animation
                            (expandHorizontally(clip = false, expandFrom = Alignment.Start) + fadeIn()
                                    // Exit animation
                                    with fadeOut())
                                .using(SizeTransform(clip = false))
                        }
                    ) {
                        Text(
                            modifier = Modifier,
                            // Quick fix to prevent the UI from crashing
                            text = it?.take(1000) ?: "",
                            textAlign = TextAlign.End,
                            softWrap = false,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                            style = NumbersTextStyleDisplayMedium
                        )
                    }
                }
            }
        }

        AnimatedContent(
            modifier = Modifier
                .align(Alignment.End)
                .padding(horizontal = 8.dp),
            targetState = helperText
        ) {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
