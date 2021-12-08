package com.the.markers.base.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.the.markers.base.navigation.NavigationDirections
import com.the.markers.base.navigation.NavigationManager
import com.the.markers.features.home.HomeComponent
import com.the.markers.features.post_details.PostDetailsComponent
import com.the.markers.features.profile.ProfileComponent
import java.lang.IllegalStateException

@ExperimentalFoundationApi
@Composable
fun NavigationComponent(navController: NavHostController, navigationManager: NavigationManager) {
    NavHost(
        navController = navController,
        startDestination = NavigationDirections.home.destination
    ) {

        composable(route = NavigationDirections.home.route) {
            HomeComponent(navigationManager)
        }
        composable(
            route = NavigationDirections.postDetails.route,
            arguments = listOf(navArgument(NavigationDirections.ARG_POST_ID) {
                type = NavType.StringType
            })
        ) {
            PostDetailsComponent(
                navigationManager = navigationManager, postId = it.arguments?.getString(
                    NavigationDirections.ARG_POST_ID
                ) ?: throw IllegalStateException("We kind of need this")
            )
        }
        composable(
            route = NavigationDirections.profile.route,
            arguments = listOf(navArgument(NavigationDirections.ARG_USER_ID) {
                type = NavType.StringType
            })
        ) {
            ProfileComponent(
                navigationManager = navigationManager, userId = it.arguments?.getString(
                    NavigationDirections.ARG_USER_ID
                ) ?: throw IllegalStateException("We kind of need this")
            )
        }
    }
}