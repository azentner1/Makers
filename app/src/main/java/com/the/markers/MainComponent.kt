package com.the.markers

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.the.markers.base.ui.NavigationComponent
import com.the.markers.base.navigation.NavigationManager
import com.the.markers.ui.theme.MarkersTheme

@ExperimentalFoundationApi
@Composable
fun MainComponent(navController: NavHostController, navigationManager: NavigationManager) {
    Scaffold(Modifier.fillMaxSize().background(color = MaterialTheme.colors.background)) {
        NavigationComponent(navController = navController, navigationManager = navigationManager)
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MarkersTheme {

    }
}