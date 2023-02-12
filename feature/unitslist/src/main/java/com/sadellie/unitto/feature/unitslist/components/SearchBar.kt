/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2023 Elshan Agaev
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

package com.sadellie.unitto.feature.unitslist.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.sadellie.unitto.core.ui.common.NavigateUpButton
import com.sadellie.unitto.feature.unitslist.R

/**
 * Search bar on the Second screen. Controls what will be shown in the list above this component
 *
 * @param title Search bar title
 * @param value Current query
 * @param onValueChange Action to perform when search query changes
 * @param favoritesOnly Current filter state: On or Off
 * @param favoriteAction Function to toggle favorite filter
 * @param navigateUpAction Function to navigate to previous screen
 * @param focusManager Used to hide keyboard when leaving unit selection screen
 * @param scrollBehavior [TopAppBarScrollBehavior] that is used for collapsing and container color
 */
@Composable
internal fun SearchBar(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    favoritesOnly: Boolean,
    favoriteAction: () -> Unit,
    navigateUpAction: () -> Unit,
    focusManager: FocusManager,
    scrollBehavior: TopAppBarScrollBehavior
) {
    var showSearch by rememberSaveable { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    fun stagedNavigateUp() {
        if (showSearch) {
            // Search text field is open, need to close it and clear search query
            onValueChange("")
            focusManager.clearFocus()
            showSearch = false
        } else {
            // No search text field is shown, can go back as usual
            navigateUpAction()
        }
    }

    TopAppBar(
        title = {
            Crossfade(targetState = showSearch) { textFieldShown ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (textFieldShown) {
                        SearchTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .focusRequester(focusRequester),
                            value = value,
                            onValueChange = onValueChange,
                            onSearch = {
                                // Close searchbar if there is nothing in search query and user
                                // clicks search button on his keyboard
                                if (value.isEmpty()) {
                                    showSearch = false
                                } else {
                                    focusManager.clearFocus()
                                }
                            }
                        )
                        LaunchedEffect(Unit) {
                            focusRequester.requestFocus()
                        }
                    } else {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            text = title,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        },
        actions = {
            Crossfade(targetState = showSearch) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    when (it) {
                        false -> {
                            // Search button
                            SearchButton {
                                onValueChange("")
                                showSearch = true
                            }
                            // Favorites button
                            FavoritesButton(favoritesOnly, favoriteAction)
                        }
                        true -> {
                            // Clear button
                            ClearButton(value.isNotBlank()) { onValueChange("") }
                        }
                    }
                }
            }
        },
        navigationIcon = {
            NavigateUpButton { stagedNavigateUp() }
        },
        scrollBehavior = scrollBehavior
    )

    BackHandler { stagedNavigateUp() }
}

@Composable
private fun SearchTextField(
    modifier: Modifier,
    value: String,
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
                    text = stringResource(R.string.search_bar_placeholder),
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
private fun FavoritesButton(
    favoritesOnly: Boolean,
    favoriteAction: () -> Unit
) {
    IconButton(onClick = favoriteAction) {
        AnimatedContent(
            targetState = favoritesOnly,
            transitionSpec = {
                (scaleIn() with scaleOut()).using(SizeTransform(clip = false))
            }
        ) {
            Icon(
                if (favoritesOnly) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = stringResource(R.string.favorite_button_description)
            )
        }
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
