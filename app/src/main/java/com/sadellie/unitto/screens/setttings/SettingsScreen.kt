package com.sadellie.unitto.screens.setttings

import android.os.Build
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.R
import com.sadellie.unitto.data.ABOUT_SCREEN
import com.sadellie.unitto.data.preferences.*
import com.sadellie.unitto.screens.MainViewModel
import com.sadellie.unitto.screens.openLink


@Composable
fun SettingsScreen(
    mainViewModel: MainViewModel,
    navigateUpAction: () -> Unit,
    navControllerAction: (String) -> Unit
) {
    val mContext = LocalContext.current

    // Scrollable
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = remember(decayAnimationSpec) {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(decayAnimationSpec)
    }

    var currentDialogState: Int by rememberSaveable { mutableStateOf(0) }
    val currentAppTheme: Int by mainViewModel.currentAppTheme.collectAsState(AppTheme.NOT_SET)

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.settings_screen))
                },
                navigationIcon = {
                    IconButton(onClick = navigateUpAction) {
                        Icon(
                            Icons.Outlined.ArrowBack,
                            contentDescription = stringResource(id = R.string.navigate_up_description)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = {
            LazyColumn(
                Modifier.fillMaxHeight()
            ) {
                item {
                    Column {
                        // Group header
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 12.dp),
                            text = stringResource(id = R.string.general_settings_group),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        SettingsListItem(
                            label = stringResource(R.string.precision_setting),
                            supportText = stringResource(R.string.precision_setting_support),
                            onClick = { currentDialogState = 1 }
                        )
                        SettingsListItem(
                            label = stringResource(R.string.separator_setting),
                            supportText = stringResource(R.string.separator_setting_support),
                            onClick = { currentDialogState = 2 }
                        )
                        SettingsListItem(
                            label = stringResource(R.string.output_format_setting),
                            supportText = stringResource(id = R.string.output_format_setting_support),
                            onClick = { currentDialogState = 3 }
                        )
                        SettingsListItem(
                            label = stringResource(R.string.theme_setting),
                            supportText = stringResource(R.string.theme_setting_support),
                            onClick = { currentDialogState = 4 }
                        )
                        SettingsListItem(
                            label = stringResource(id = R.string.currency_rates_note_setting),
                            onClick = { currentDialogState = 5 }
                        )

                        // Group header
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 12.dp),
                            text = stringResource(id = R.string.additional_settings_group),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        SettingsListItem(
                            label = stringResource(R.string.terms_and_conditions),
                            onClick = {
                                openLink(
                                    mContext,
                                    "http://sadellie.github.io/unitto/terms-app.html"
                                )
                            }
                        )
                        SettingsListItem(
                            label = stringResource(R.string.privacy_policy),
                            onClick = {
                                openLink(
                                    mContext,
                                    "http://sadellie.github.io/unitto/privacy-app.html"
                                )
                            }
                        )
                        SettingsListItem(
                            label = stringResource(R.string.third_party_licenses),
                            onClick = { navControllerAction(ABOUT_SCREEN) }
                        )
                        SettingsListItem(
                            label = stringResource(R.string.rate_this_app),
                            onClick = {
                                openLink(
                                    mContext,
                                    "http://play.google.com/store/apps/details?id=com.sadellie.unitto"
                                )
                            }
                        )
                    }
                }
            }
        },
    )

    // Showing dialog
    when (currentDialogState) {
        1 -> {
            AlertDialogWithList(
                title = stringResource(id = R.string.precision_setting),
                listItems = PRECISIONS,
                selectedItemIndex = mainViewModel.precision,
                selectAction = { mainViewModel.setPrecisionPref(it) },
                dismissAction = { currentDialogState = 0 },
                supportText = stringResource(id = R.string.precision_setting_info)
            )
        }
        2 -> {
            AlertDialogWithList(
                title = stringResource(id = R.string.separator_setting),
                listItems = SEPARATORS,
                selectedItemIndex = mainViewModel.separator,
                selectAction = { mainViewModel.setSeparatorPref(it) },
                dismissAction = { currentDialogState = 0 }
            )
        }
        3 -> {
            AlertDialogWithList(
                title = stringResource(id = R.string.output_format_setting),
                listItems = OUTPUT_FORMAT,
                selectedItemIndex = mainViewModel.outputFormat,
                selectAction = { mainViewModel.setOutputFormatPref(it) },
                dismissAction = { currentDialogState = 0 },
                supportText = stringResource(id = R.string.output_format_setting_info)
            )
        }
        4 -> {
            AlertDialogWithList(
                title = stringResource(id = R.string.theme_setting),
                listItems = APP_THEMES,
                selectedItemIndex = currentAppTheme,
                selectAction = { mainViewModel.saveCurrentAppTheme(it) },
                dismissAction = { currentDialogState = 0 },
                // Show note for users with devices that support custom Dynamic theming
                supportText = if (Build.VERSION.SDK_INT in (Build.VERSION_CODES.O_MR1..Build.VERSION_CODES.R)) stringResource(
                    id = R.string.theme_setting_info
                ) else null
            )
        }
        5 -> {
            AlertDialogWithList(
                title = stringResource(id = R.string.currency_rates_note_title),
                dismissAction = { currentDialogState = 0 },
                supportText = stringResource(id = R.string.currency_rates_note_text)
            )
        }
        // Dismissing alert dialog
        else -> {}
    }
}
