/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.NavigateUpButton
import com.sadellie.unitto.core.ui.common.ListItem
import com.sadellie.unitto.core.ui.common.ScaffoldWithLargeTopBar
import com.sadellie.unitto.core.ui.openLink
import com.sadellie.unitto.feature.settings.components.AnnoyingBox

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

    ScaffoldWithLargeTopBar(
        title = stringResource(R.string.settings_language),
        navigationIcon = { NavigateUpButton(navigateUp) }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            item("translate") {
                AnnoyingBox(
                    modifier = Modifier.padding(16.dp, 8.dp).fillMaxWidth(),
                    imageVector = Icons.Default.Translate,
                    imageVectorContentDescription = stringResource(R.string.settings_translate_app),
                    title = stringResource(R.string.settings_translate_app),
                    support = stringResource(R.string.settings_translate_app_support)
                ) {
                    openLink(mContext, "https://poeditor.com/join/project/T4zjmoq8dx")
                }
            }

            languages.forEach { (key, res) ->
                item(key) {
                    ListItem(
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
