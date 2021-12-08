package com.the.markers.base.navigation

interface NavigationItem {
    var destination: String
    fun addArg(arg: String, value: String)
    val route: String
}