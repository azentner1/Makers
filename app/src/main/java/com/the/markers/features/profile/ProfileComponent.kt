package com.the.markers.features.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.the.markers.base.model.UiDataState
import com.the.markers.features.profile.model.ProfileData
import com.the.markers.base.ui.LoadingComponent
import com.the.markers.base.navigation.NavigationDirections
import com.the.markers.base.navigation.NavigationManager
import com.the.markers.ui.theme.MarkersTheme
import com.the.markers.R
import com.the.markers.features.home.*
import com.the.markers.base.navigation.NavigationItem

@Composable
fun ProfileComponent(
    navigationManager: NavigationManager,
    viewModel: ProfileViewDataModel = hiltViewModel(),
    userId: String
) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
        val uiState by viewModel.dataState.asFlow().collectAsState(UiDataState.Idle)
        when (uiState) {
            is UiDataState.Idle -> {}
            is UiDataState.Loading -> {
                LoadingComponent()
            }
            is UiDataState.Error -> {
                PostListErrorComponent()
            }
            is UiDataState.Success -> {
                ProfileContent(
                    uiState as UiDataState.Success<ProfileData>,
                    navigationManager
                )
            }
        }
        viewModel.setStateForEvent(ProfileEvent.FetchProfile(userId = userId))
    }
}

@Composable
fun ProfileContent(
    uiState: UiDataState.Success<ProfileData>,
    navigationManager: NavigationManager
) {
    when (uiState.data) {
        is ProfileData.ProfileDataModel -> {
            uiState.data.profile?.let { profile ->
                ProfileContentComponent(
                    profile = profile,
                    navigationManager = navigationManager
                )
            }
        }
    }
}

@Composable
fun ProfileContentComponent(
    profile: ProfileData.Profile,
    navigationManager: NavigationManager
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.primary)
                .padding(16.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(96.dp)
                    .align(Alignment.CenterHorizontally),
                painter = rememberImagePainter(data = profile.profileImage, builder = {
                    transformations(CircleCropTransformation())
                }),
                contentDescription = ""
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = profile.name,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = profile.username,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.voted_posts),
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(8.dp))
            VotedPostsComponent(posts = profile.votedPosts, navigationManager = navigationManager)
        }
    }
}

@Composable
fun VotedPostsComponent(posts: List<ProfileData.VotedPostData>, navigationManager: NavigationManager) {
    LazyColumn(contentPadding = PaddingValues(vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(posts) { post ->
            VotedPostComponent(post, navigationManager)
        }
    }
}

@Composable
fun VotedPostComponent(post: ProfileData.VotedPostData, navigationManager: NavigationManager) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                profileNavigationDelegate(navigationManager,
                    NavigationDirections.postDetails.apply {
                        addArg(NavigationDirections.ARG_POST_ID, post.id)
                    }
                )
            },
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colors.background)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Image(modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp)), painter = rememberImagePainter(data = post.thumbnail, builder = {
                    crossfade(true)
                }), contentDescription = "")
                Text(text = post.hunter.username, style = MaterialTheme.typography.caption, fontSize = 12.sp)
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

fun profileNavigationDelegate(navigationManager: NavigationManager, navigationItem: NavigationItem) {
    navigationManager.navigate(navigationItem)
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MarkersTheme {
    }
}