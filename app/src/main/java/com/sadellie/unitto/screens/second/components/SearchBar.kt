package com.sadellie.unitto.screens.second.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.sadellie.unitto.R

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
fun SearchBar(
    title: String = String(),
    value: String = String(),
    onValueChange: (String) -> Unit = {},
    favoritesOnly: Boolean,
    favoriteAction: () -> Unit,
    navigateUpAction: () -> Unit = {},
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

    SmallTopAppBar(
        title = {
            Crossfade(targetState = showSearch) { textFieldShown ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    when (textFieldShown) {
                        // No search text field
                        false -> {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                text = title,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                        // With text field
                        true -> {
                            BasicTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .focusRequester(focusRequester),
                                value = value,
                                onValueChange = onValueChange,
                                singleLine = true,
                                textStyle = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                                cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                                keyboardActions = KeyboardActions(onSearch = {
                                    // Close searchbar if there is nothing in search query and user
                                    // clicks search button on his keyboard
                                    if (value.isEmpty()) {
                                        showSearch = false
                                    } else {
                                        focusManager.clearFocus()
                                    }
                                }),
                                decorationBox = { innerTextField ->
                                    // Showing placeholder only when there is query is empty
                                    if (value.isEmpty()) {
                                        innerTextField()
                                        Text(
                                            modifier = Modifier.alpha(0.7f),
                                            text = stringResource(id = R.string.search_bar_placeholder),
                                            style = MaterialTheme.typography.titleLarge,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    } else {
                                        innerTextField()
                                    }
                                }
                            )
                            LaunchedEffect(Unit) {
                                focusRequester.requestFocus()
                            }
                        }
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
                            IconButton(onClick = { onValueChange(""); showSearch = true }) {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = stringResource(id = R.string.search_button_description)
                                )
                            }
                            // Favorites button
                            IconButton(onClick = favoriteAction) {
                                AnimatedContent(
                                    targetState = favoritesOnly,
                                    transitionSpec = {
                                        (scaleIn() with scaleOut()).using(SizeTransform(clip = false))
                                    }
                                ) {
                                    Icon(
                                        if (favoritesOnly) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                        contentDescription = stringResource(id = R.string.favorite_button_description)
                                    )
                                }
                            }
                        }
                        true -> {
                            // Clear button
                            IconButton(onClick = { onValueChange("") }) {
                                Icon(
                                    modifier = Modifier.alpha(if (value.isBlank()) 0f else 1f),
                                    imageVector = Icons.Outlined.Clear,
                                    contentDescription = stringResource(id = R.string.clear_input_description)
                                )
                            }
                        }
                    }
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = { stagedNavigateUp() }) {
                Icon(
                    Icons.Outlined.ArrowBack,
                    contentDescription = stringResource(id = R.string.navigate_up_description)
                )
            }
        },
        scrollBehavior = scrollBehavior
    )

    BackHandler { stagedNavigateUp() }
}
