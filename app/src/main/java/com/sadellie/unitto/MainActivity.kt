package com.sadellie.unitto

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sadellie.unitto.data.*
import com.sadellie.unitto.data.preferences.AppTheme
import com.sadellie.unitto.screens.MainViewModel
import com.sadellie.unitto.screens.about.AboutScreen
import com.sadellie.unitto.screens.main.MainScreen
import com.sadellie.unitto.screens.second.SecondScreen
import com.sadellie.unitto.screens.setttings.SettingsScreen
import com.sadellie.unitto.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val currentAppTheme: Int by mainViewModel.currentAppTheme.collectAsState(AppTheme.NOT_SET)

            // We don't draw anything until we know what theme we need to use
            if (currentAppTheme != AppTheme.NOT_SET) {
                AppTheme(
                    currentAppTheme = currentAppTheme
                ) {
                    UnittoApp(
                        navController = navController,
                        viewModel = mainViewModel,
                    )
                }
            }
        }
    }

    override fun onPause() {
        mainViewModel.saveMe()
        super.onPause()
    }
}

@Composable
fun UnittoApp(
    navController: NavHostController,
    viewModel: MainViewModel,
) {
    Scaffold { padding ->
        NavHost(modifier = Modifier.padding(padding), navController = navController, startDestination = MAIN_SCREEN) {

            composable(MAIN_SCREEN) {
                MainScreen(
                    navControllerAction = { route -> navController.navigate(route) },
                    viewModel = viewModel
                )
            }

            composable(
                "$SECOND_SCREEN/{$LEFT_BUTTON}",
                arguments = listOf(navArgument(LEFT_BUTTON) { type = NavType.BoolType })
            ) {
                val leftButton = it.arguments?.getBoolean(LEFT_BUTTON) ?: true
                SecondScreen(
                    viewModel = viewModel,
                    leftSide = leftButton,
                    navigateUp = { navController.navigateUp() },
                )
            }

            composable(SETTINGS_SCREEN) {
                SettingsScreen(
                    mainViewModel = viewModel,
                    navigateUpAction = { navController.navigateUp() },
                    navControllerAction = { route -> navController.navigate(route) }
                )
            }

            composable(ABOUT_SCREEN) {
                AboutScreen(navigateUpAction = { navController.navigateUp() })
            }
        }
    }
}
