package com.sadellie.unitto

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.sadellie.unitto.core.navigation.DrawerItem

/** Navigates to clicked [destination]. */
internal fun navigateToTab(destination: DrawerItem, navController: NavHostController) {
  navController.navigate(destination.graphRoute) {
    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
    launchSingleTop = true
    restoreState = true
  }
}
