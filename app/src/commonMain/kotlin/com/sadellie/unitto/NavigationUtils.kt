package com.sadellie.unitto

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.sadellie.unitto.core.navigation.DrawerItem

/** Navigates to clicked [destination]. */
internal fun navigateToTab(destination: DrawerItem, backStack: NavBackStack<NavKey>) {
  // single top
  if (backStack.lastOrNull() == destination.topLevelRoute) return
  val root = backStack.firstOrNull()
  backStack.removeAll { it != root }
  backStack.add(destination.topLevelRoute)
}
