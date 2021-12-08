package com.the.markers.base.navigation

object NavigationDirections {

    val home = object : NavigationItem {
        override var destination: String = "home"
        override fun addArg(arg: String, value: String) {

        }
        override val route: String by lazy { ROUTE_HOME }
    }

    var postDetails = object : NavigationItem {
        override var destination: String = "post_details"
        override fun addArg(arg: String, value: String) {
            destination = "post_details/$value"
        }
        override val route: String by lazy { ROUTE_POST_DETAILS }
    }

    var profile = object : NavigationItem {
        override var destination: String = "profile"
        override fun addArg(arg: String, value: String) {
            destination = "profile/$value"
        }
        override val route: String by lazy { ROUTE_PROFILE }
    }

    var default = object : NavigationItem {
        override var destination: String = "default"
        override fun addArg(arg: String, value: String) {

        }
        override val route: String by lazy { ROUTE_DEFAULT }
    }

    const val ARG_POST_ID = "postId"
    const val ARG_USER_ID = "userId"

    private const val ROUTE_HOME = "home"
    private const val ROUTE_PROFILE = "profile/{userId}"
    private const val ROUTE_POST_DETAILS = "post_details/{postId}"
    private const val ROUTE_DEFAULT = "default"
}