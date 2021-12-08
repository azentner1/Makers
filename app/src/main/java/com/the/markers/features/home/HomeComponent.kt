package com.the.markers.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import coil.compose.rememberImagePainter
import com.the.markers.R
import com.the.markers.base.model.UiDataState
import com.the.markers.base.test.TestTags
import com.the.markers.features.home.model.HomeData
import com.the.markers.base.ui.LoadingComponent
import com.the.markers.base.navigation.NavigationDirections
import com.the.markers.base.navigation.NavigationItem
import com.the.markers.base.navigation.NavigationManager
import com.the.markers.ui.theme.MarkersTheme

@Composable
fun HomeComponent(navigationManager: NavigationManager, viewModel: HomeViewModel = hiltViewModel()) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
        val uiState by viewModel.dataState.asFlow().collectAsState(UiDataState.Idle)
        when (uiState) {
            is UiDataState.Idle -> {}
            is UiDataState.Loading -> {
               LoadingComponent()
            }
            is UiDataState.Error -> {

            }
            is UiDataState.Success -> {
                PostContentComponent(uiState as UiDataState.Success<HomeData>, navigationManager)
            }
        }
        viewModel.setStateForEvent(HomeEvent.FetchPosts)
    }
}

@Composable
fun PostContentComponent(uiState: UiDataState.Success<HomeData>, navigationManager: NavigationManager) {
    when (uiState.data) {
        is HomeData.PostDataModel -> {
            PostListComponent(posts = uiState.data.postList, navigationManager = navigationManager)
        }
    }
}

@Composable
fun PostListComponent(posts: List<HomeData.PostData>, navigationManager: NavigationManager) {
    LazyColumn(modifier = Modifier.testTag(TestTags.TAG_HOME_POST_LIST), contentPadding = PaddingValues(vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(posts) { post ->
            PostComponent(post, navigationManager)
        }
    }
}

@Composable
fun PostComponent(post: HomeData.PostData, navigationManager: NavigationManager) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                homeNavigationDelegate(navigationManager,
                    NavigationDirections.postDetails.apply {
                        addArg(NavigationDirections.ARG_POST_ID, post.id)
                    }
                )
            },
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colors.primary)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Image(modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp)), painter = rememberImagePainter(data = post.image, builder = {
                    crossfade(true)
                }), contentDescription = "")
                Text(text = post.hunter.name, style = MaterialTheme.typography.caption, fontSize = 12.sp)
            }

            Column(modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f)) {
                Text(text = post.name, style = MaterialTheme.typography.subtitle1)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = post.tagline, style = MaterialTheme.typography.caption)
                Spacer(modifier = Modifier.height(1.dp))
                Text(text = stringResource(R.string.comments, post.commentCount.toString()), style = MaterialTheme.typography.overline)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = post.votesCount.toString(), style = MaterialTheme.typography.overline, fontSize = 16.sp)
                Image(modifier = Modifier.wrapContentSize(), painter = painterResource(id = R.drawable.ic_baseline_arrow_drop_up_24), contentDescription = "")
            }
        }
    }
}

@Composable
fun PostListErrorComponent() {

}

fun homeNavigationDelegate(navigationManager: NavigationManager, navigationItem: NavigationItem) {
    navigationManager.navigate(navigationItem)
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MarkersTheme {

    }
}