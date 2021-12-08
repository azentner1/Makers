package com.the.markers.base.navigation

import kotlinx.coroutines.flow.MutableStateFlow

class NavigationManager {
    var commands = MutableStateFlow(NavigationDirections.default)

    fun navigate(
        directions: NavigationItem
    ) {
        commands.value = directions
    }
}