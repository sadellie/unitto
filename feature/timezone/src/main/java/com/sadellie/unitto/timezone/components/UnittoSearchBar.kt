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

package com.sadellie.unitto.timezone.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.NavigateUpButton

@Composable
fun UnittoSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    navigateUp: () -> Unit,
    title: String,
    searchActions: @Composable (RowScope.() -> Unit) = {},
    noSearchActions: @Composable (RowScope.() -> Unit) = {},
    placeholder: String,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors()
) {
    var showSearch by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    fun stagedNavigateUp() {
        if (showSearch) {
            // Search text field is open, need to close it and clear search query
            showSearch = false
            // focusManager.clearFocus()
            onQueryChange("")
        } else {
            // No search text field is shown, can go back as usual
            navigateUp()
        }
    }

    TopAppBar(
        modifier = modifier,
        title = {
            Crossfade(showSearch) { _showSearch ->
                if (_showSearch) {
                    LaunchedEffect(Unit) { focusRequester.requestFocus() }

                    SearchTextField(
                        modifier = Modifier
                            .focusRequester(focusRequester),
                        value = query,
                        placeholder = placeholder,
                        onValueChange = onQueryChange,
                        onSearch = {}
                    )
                } else {
                    Text(
                        text = title,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        },
        navigationIcon = {
            NavigateUpButton { stagedNavigateUp() }
        },
        actions = {
            Crossfade(showSearch) { _showSearch ->
                if (_showSearch) {
                    ClearButton(visible = query.isNotEmpty()) { onQueryChange("") }
                    searchActions()
                } else {
                    SearchButton { showSearch = true }
                    noSearchActions()
                }
            }
        },
        scrollBehavior = scrollBehavior,
        colors = colors,
    )

    BackHandler { stagedNavigateUp() }
}

@Composable
private fun SearchTextField(
    modifier: Modifier,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    onSearch: KeyboardActionScope.() -> Unit
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onSurface),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = onSearch),
        decorationBox = { innerTextField ->
            innerTextField()
            // Showing placeholder only when there is query is empty
            value.ifEmpty {
                Text(
                    modifier = Modifier.alpha(0.7f),
                    text = placeholder,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    )
}

@Composable
private fun SearchButton(
    onClick: () -> Unit
) {
    IconButton(onClick) {
        Icon(
            Icons.Default.Search,
            contentDescription = stringResource(R.string.search_button_description)
        )
    }
}

@Composable
private fun ClearButton(
    visible: Boolean,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Icon(
                imageVector = Icons.Outlined.Clear,
                contentDescription = stringResource(R.string.clear_input_description)
            )
        }
    }
}
