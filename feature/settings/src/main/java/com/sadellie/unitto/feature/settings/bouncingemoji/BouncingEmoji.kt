/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
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

package com.sadellie.unitto.feature.settings.bouncingemoji

import androidx.annotation.FloatRange
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.ui.common.NavigateUpButton
import com.sadellie.unitto.core.ui.common.ScaffoldWithTopBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
internal fun BouncingEmojiRoute(
    navigateUpAction: () -> Unit,
) {
    ScaffoldWithTopBar(
        title = { AnimatedText("Bouncy boy") },
        navigationIcon = { NavigateUpButton(navigateUpAction) }
    ) { paddingValues ->
        BouncingEmojiScreen(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        )
    }
}

/**
 * @param modifier [Modifier] that will be applied to the surrounding [BoxWithConstraints].
 * @param initialX Initial horizontal position. 0 means left, 1 means right.
 * @param initialY Initial vertical position. 0 means top, 1 means bottom.
 */
@Composable
private fun BouncingEmojiScreen(
    modifier: Modifier,
    @FloatRange(0.0, 1.0) initialX: Float = Random.nextFloat(),
    @FloatRange(0.0, 1.0) initialY: Float = Random.nextFloat(),
) {
    val density = LocalDensity.current
    var speed by remember { mutableFloatStateOf(1f) }

    CompositionLocalProvider(
        value = LocalDensity provides Density(density.density, fontScale = 1f)
    ) {
        BoxWithConstraints(
            modifier = modifier.clickable {
                speed = Random.nextFloat()
            }
        ) {
            val width = constraints.maxWidth
            val height = constraints.maxHeight
            val ballSize = 96.dp
            val ballSizePx = with(LocalDensity.current) { ballSize.toPx().roundToInt() }

            var x by rememberSaveable { mutableFloatStateOf((width - ballSizePx) * initialX) }
            var y by rememberSaveable { mutableFloatStateOf((height - ballSizePx) * initialY) }

            val animatedX = animateFloatAsState(x)
            val animatedY = animateFloatAsState(y)

            var xSpeed by rememberSaveable { mutableFloatStateOf(10f) }
            var ySpeed by rememberSaveable { mutableFloatStateOf(10f) }

            var bounces by remember { mutableFloatStateOf(0f) }
            var edgeHits by rememberSaveable { mutableFloatStateOf(0f) }
            var emoji by remember { mutableStateOf("❤") }
            var mood by remember { mutableStateOf("prepare for impact") }

            Column(
                modifier = Modifier.offset {
                    IntOffset(
                        animatedX.value.roundToInt(),
                        animatedY.value.roundToInt()
                    )
                },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                        .size(ballSize),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedText(emoji, MaterialTheme.typography.displayMedium)
                }
                AnimatedText(mood)
            }

            Column {
                AnimatedText("Speed: ${xSpeed.absoluteValue * speed}")
                AnimatedText("Bounces: $bounces")
                AnimatedText("Edge hits: $edgeHits")
                AnimatedText("Luck: ${edgeHits / bounces}")
            }

            LaunchedEffect(
                key1 = Unit,
                key2 = speed
            ) {
                while (isActive) {
                    x += xSpeed * speed
                    y += ySpeed * speed

                    val rightBounce = x > width - ballSizePx
                    val leftBounce = x < 0
                    val bottomBounce = y > height - ballSizePx
                    val topBounce = y < 0
                    var bouncedEdges = 0

                    if (rightBounce || leftBounce) {
                        xSpeed = -xSpeed
                        bouncedEdges++
                    }

                    if (topBounce || bottomBounce) {
                        ySpeed = -ySpeed
                        bouncedEdges++
                    }

                    // Count edge hit as 1 bounce
                    when(bouncedEdges) {
                        2 -> {
                            edgeHits++
                            bounces++
                            mood = winnerMood.random()
                            emoji = winnerEmoji.random()
                        }
                        1 -> {
                            bounces++
                            mood = looserMood.random()
                            emoji = looserEmoji.random()
                        }
                    }

                    delay(1)
                }
            }
        }
    }
}

@Composable
private fun AnimatedText(
    text: String,
    style: TextStyle = LocalTextStyle.current
) {
    AnimatedContent(
        targetState = text,
        transitionSpec = {
            slideInVertically { height -> height } + fadeIn() togetherWith
                    slideOutVertically { height -> -height } + fadeOut()
        }
    ) {
        Text(
            text = it,
            style = style,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

private val looserEmoji by lazy {
    listOf("🤡", "😭", "👿", "💀", "💩")
}

private val winnerEmoji by lazy {
    listOf("🤠", "🤑", "😎", "🥇")
}

private val looserMood by lazy {
    listOf("sus", "bruh", "no cap", "fr fr", "vibing", "oof", "F", "mood", "L+ratio", "yeet")
}

private val winnerMood by lazy {
    listOf("ayoo", "W", "skill", "sheeesh", "bro is cheating")
}

@Preview
@Composable
private fun PreviewBouncingEmojiScreen() {
    BouncingEmojiScreen(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .size(400.dp, 550.dp),
        initialX = 0.5f,
        initialY = 0.5f,
    )
}
