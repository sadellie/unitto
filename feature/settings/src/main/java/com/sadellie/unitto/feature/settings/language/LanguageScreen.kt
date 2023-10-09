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

package com.sadellie.unitto.feature.settings.language

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.NavigateUpButton
import com.sadellie.unitto.core.ui.common.UnittoListItem
import com.sadellie.unitto.core.ui.common.UnittoScreenWithLargeTopBar
import com.sadellie.unitto.core.ui.common.squashable
import com.sadellie.unitto.core.ui.openLink

@Composable
internal fun LanguageRoute(
    navigateUp: () -> Unit,
) {
    LanguageScreen(
        navigateUp = navigateUp
    )
}

@Composable
private fun LanguageScreen(
    navigateUp: () -> Unit,
) {
    val mContext = LocalContext.current
    val currentLangKey = remember {
        AppCompatDelegate.getApplicationLocales().toLanguageTags()
    }

    fun changeLanguage(langKey: String) {
        val selectedLocale = if (langKey == "") LocaleListCompat.getEmptyLocaleList()
        else LocaleListCompat.forLanguageTags(langKey)

        AppCompatDelegate.setApplicationLocales(selectedLocale)
        navigateUp()
    }

    UnittoScreenWithLargeTopBar(
        title = stringResource(R.string.settings_language),
        navigationIcon = { NavigateUpButton(navigateUp) }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            item("translate") {
                Box(Modifier.padding(16.dp, 8.dp)) {
                    Row(
                        modifier = Modifier
                            .squashable(
                                onClick = {
                                    openLink(mContext, "https://poeditor.com/join/project/T4zjmoq8dx")
                                },
                                interactionSource = remember { MutableInteractionSource() },
                                cornerRadiusRange = 15..50
                            )
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .padding(16.dp, 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Translate,
                            contentDescription = stringResource(R.string.settings_translate_app),
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Column(
                            Modifier
                                .weight(1f)
                                .padding(vertical = 8.dp)) {
                            Text(
                                text = stringResource(R.string.settings_translate_app),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                            )
                            Text(
                                text = stringResource(R.string.settings_translate_app_support),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                            )
                        }
                    }
                }
            }

            languages.forEach { (key, res) ->
                item(key) {
                    UnittoListItem(
                        modifier = Modifier.clickable { changeLanguage(key) },
                        headlineContent = {
                            Text(stringResource(res))
                        },
                        leadingContent = {
                            RadioButton(
                                selected = currentLangKey == key,
                                onClick = { changeLanguage(key) }
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LanguageScreenPreview() {
    LanguageScreen(
        navigateUp = {}
    )
}
