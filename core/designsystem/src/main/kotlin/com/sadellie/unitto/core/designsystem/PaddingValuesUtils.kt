package com.sadellie.unitto.core.designsystem

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

// from https://android-review.googlesource.com/c/platform/frameworks/support/+/3776106
// TODO remove in 1.10.0
/** Adds two [PaddingValues] together. */
@Stable
operator fun PaddingValues.plus(other: PaddingValues): PaddingValues =
  object : PaddingValues {
    override fun calculateLeftPadding(layoutDirection: LayoutDirection): Dp =
      this@plus.calculateLeftPadding(layoutDirection) + other.calculateLeftPadding(layoutDirection)

    override fun calculateTopPadding(): Dp =
      this@plus.calculateTopPadding() + other.calculateTopPadding()

    override fun calculateRightPadding(layoutDirection: LayoutDirection): Dp =
      this@plus.calculateRightPadding(layoutDirection) +
        other.calculateRightPadding(layoutDirection)

    override fun calculateBottomPadding(): Dp =
      this@plus.calculateBottomPadding() + other.calculateBottomPadding()
  }

/** Subtracts a [PaddingValues] from another one. */
@Stable
operator fun PaddingValues.minus(other: PaddingValues): PaddingValues =
  object : PaddingValues {
    override fun calculateLeftPadding(layoutDirection: LayoutDirection): Dp =
      (this@minus.calculateLeftPadding(layoutDirection) -
          other.calculateLeftPadding(layoutDirection))
        .coerceAtLeast(0.dp)

    override fun calculateTopPadding(): Dp =
      (this@minus.calculateTopPadding() - other.calculateTopPadding()).coerceAtLeast(0.dp)

    override fun calculateRightPadding(layoutDirection: LayoutDirection): Dp =
      (this@minus.calculateRightPadding(layoutDirection) -
          other.calculateRightPadding(layoutDirection))
        .coerceAtLeast(0.dp)

    override fun calculateBottomPadding(): Dp =
      (this@minus.calculateBottomPadding() - other.calculateBottomPadding()).coerceAtLeast(0.dp)
  }
