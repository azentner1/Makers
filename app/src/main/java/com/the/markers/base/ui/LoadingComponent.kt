package com.the.markers.base.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.the.markers.base.test.TestTags

@Composable
fun LoadingComponent() {
    Box(modifier = Modifier
        .background(MaterialTheme.colors.background)
        .fillMaxSize().testTag(TestTags.TAG_LOADING_INDICATOR),
        contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}