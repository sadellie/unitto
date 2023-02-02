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

package com.sadellie.unitto.feature.epoch.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.ui.theme.NumbersTextStyleDisplayMedium
import com.sadellie.unitto.feature.epoch.R
import kotlinx.coroutines.delay

@Composable
fun DateTextField(
    modifier: Modifier,
    date: String
) {
    val inputWithPadding: String = date.padEnd(14, '0')

    fun inFocus(range: Int): Boolean = date.length > range

    Column(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .horizontalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.End
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            // Hour
            AnimatedText(text = inputWithPadding[0].toString(), focus = inFocus(0))
            AnimatedText(text = inputWithPadding[1].toString(), focus = inFocus(1))
            // Divider
            SimpleText(text = ":", focus = inFocus(2))
            // Minute
            AnimatedText(text = inputWithPadding[2].toString(), focus = inFocus(2))
            AnimatedText(text = inputWithPadding[3].toString(), focus = inFocus(3))
            // Divider
            SimpleText(text = ":", focus = inFocus(4))
            // Second
            AnimatedText(text = inputWithPadding[4].toString(), focus = inFocus(4))
            AnimatedText(text = inputWithPadding[5].toString(), focus = inFocus(5))
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Row {
                AnimatedText(text = inputWithPadding[6].toString(), focus = inFocus(6))
                AnimatedText(text = inputWithPadding[7].toString(), focus = inFocus(7))
                SimpleText(text = stringResource(R.string.day_short), focus = inFocus(6))
            }
            Row {
                AnimatedText(text = inputWithPadding[8].toString(), focus = inFocus(8))
                AnimatedText(text = inputWithPadding[9].toString(), focus = inFocus(9))
                SimpleText(text = stringResource(R.string.month_short), focus = inFocus(8))
            }
            Row {
                AnimatedText(text = inputWithPadding[10].toString(), focus = inFocus(10))
                AnimatedText(text = inputWithPadding[11].toString(), focus = inFocus(11))
                AnimatedText(text = inputWithPadding[12].toString(), focus = inFocus(12))
                AnimatedText(text = inputWithPadding[13].toString(), focus = inFocus(13))
                SimpleText(text = stringResource(R.string.year_short), focus = inFocus(10))
            }
        }
    }
}

@Composable
private fun AnimatedText(text: String, focus: Boolean) {
    AnimatedContent(
        targetState = text,
        transitionSpec = {
            if (targetState.toInt() > initialState.toInt()) {
                slideInVertically { height -> height } + fadeIn() with
                        slideOutVertically { height -> -height } + fadeOut()
            } else {
                slideInVertically { height -> -height } + fadeIn() with
                        slideOutVertically { height -> height } + fadeOut()
            }.using(
                SizeTransform(clip = false)
            )
        }
    ) {
        SimpleText(text = text, focus = focus)
    }
}

@Composable
private fun SimpleText(text: String, focus: Boolean) {
    val color = animateColorAsState(
        if (focus) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.outline
    )
    Text(
        text = text,
        style = NumbersTextStyleDisplayMedium,
        color = color.value
    )
}

@Preview
@Composable
private fun PreviewDateTextField() {
    var date: String by remember { mutableStateOf("2") }

    DateTextField(modifier = Modifier, date = date)

    LaunchedEffect(Unit) {
        "3550002011999".forEach {
            date += it.toString()
            delay(2500L)
        }
    }
}
