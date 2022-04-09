package com.sadellie.unitto.screens.main.components

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.sadellie.unitto.R
import com.sadellie.unitto.screens.Formatter
import com.sadellie.unitto.ui.theme.NumbersTextStyleDisplayLarge


/**
 * Component for input and output
 *
 * @param modifier Modifier that is applied to [LazyRow]
 * @param currentText Current text to show
 * @param helperText Helper text below current text (short unit name)
 * @param showLoading Show "Loading" text
 * @param showError Show "Error" text
 */
@Composable
fun MyTextField(
    modifier: Modifier = Modifier,
    currentText: String = String(),
    helperText: String = String(),
    showLoading: Boolean = false,
    showError: Boolean = false
) {
    val clipboardManager = LocalClipboardManager.current
    val mc = LocalContext.current
    val textToShow = when {
        showError -> stringResource(id = R.string.error_label)
        showLoading -> stringResource(id = R.string.loading_label)
        else -> Formatter.format(currentText)
    }
    val copiedText: String = stringResource(R.string.copied, textToShow)

    Column {
        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .combinedClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = {},
                    onLongClick = {
                        clipboardManager.setText(AnnotatedString(currentText))
                        Toast
                            .makeText(mc, copiedText, Toast.LENGTH_SHORT)
                            .show()
                    }
                ),
            reverseLayout = true,
            horizontalArrangement = Arrangement.End
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
                        text = it,
                        textAlign = TextAlign.End,
                        softWrap = false,
                        style = NumbersTextStyleDisplayLarge
                    )
                }
            }
        }
        AnimatedContent(
            modifier = Modifier.align(Alignment.End),
            targetState = helperText
        ) {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
