package com.sadellie.unitto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sadellie.unitto.data.ABOUT_SCREEN
import com.sadellie.unitto.data.LEFT_LIST_SCREEN
import com.sadellie.unitto.data.MAIN_SCREEN
import com.sadellie.unitto.data.RIGHT_LIST_SCREEN
import com.sadellie.unitto.data.SETTINGS_SCREEN
import com.sadellie.unitto.data.preferences.AppTheme
import com.sadellie.unitto.screens.MainViewModel
import com.sadellie.unitto.screens.about.AboutScreen
import com.sadellie.unitto.screens.main.MainScreen
import com.sadellie.unitto.screens.second.LeftSideScreen
import com.sadellie.unitto.screens.second.RightSideScreen
import com.sadellie.unitto.screens.second.SecondViewModel
import com.sadellie.unitto.screens.setttings.SettingsScreen
import com.sadellie.unitto.ui.theme.UnittoTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private val secondViewModel: SecondViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val currentAppTheme: Int = mainViewModel.currentTheme

            // We don't draw anything until we know what theme we need to use
            if (currentAppTheme != AppTheme.NOT_SET) {
                UnittoTheme(
                    currentAppTheme = currentAppTheme
                ) {
                    UnittoApp(
                        navController = navController,
                        mainViewModel = mainViewModel,
                        secondViewModel = secondViewModel
                    )
                }
            }
        }
    }

    override fun onPause() {
        mainViewModel.saveLatestPairOfUnits()
        super.onPause()
    }
}

@Composable
fun UnittoApp(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    secondViewModel: SecondViewModel
) {
    NavHost(
        navController = navController,
        startDestination = MAIN_SCREEN
    ) {

        composable(MAIN_SCREEN) {
            MainScreen(
                navControllerAction = { route -> navController.navigate(route) },
                viewModel = mainViewModel
            )
        }

        composable(LEFT_LIST_SCREEN) {
            LeftSideScreen(
                currentUnit = mainViewModel.unitFrom,
                navigateUp = { navController.navigateUp() },
                selectAction = { mainViewModel.changeUnitFrom(it) },
                viewModel = secondViewModel
            )
        }

        composable(RIGHT_LIST_SCREEN) {
            RightSideScreen(
                currentUnit = mainViewModel.unitTo,
                navigateUp = { navController.navigateUp() },
                selectAction = { mainViewModel.changeUnitTo(it) },
                viewModel = secondViewModel,
                inputValue = mainViewModel.mainUIState.inputValue.toBigDecimal(),
                unitFrom = mainViewModel.unitFrom
            )
        }

        composable(SETTINGS_SCREEN) {
            SettingsScreen(
                mainViewModel = mainViewModel,
                navigateUpAction = { navController.navigateUp() },
                navControllerAction = { route -> navController.navigate(route) }
            )
        }

        composable(ABOUT_SCREEN) {
            AboutScreen(navigateUpAction = { navController.navigateUp() })
        }
    }
}
