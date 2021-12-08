package com.the.markers.features.post_details

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.the.markers.R
import com.the.markers.base.model.UiDataState
import com.the.markers.features.home.PostListErrorComponent
import com.the.markers.features.post_details.model.PostDetailsData
import com.the.markers.base.ui.LoadingComponent
import com.the.markers.base.navigation.NavigationDirections
import com.the.markers.base.navigation.NavigationItem
import com.the.markers.base.navigation.NavigationManager
import com.the.markers.ui.theme.MarkersTheme

@ExperimentalFoundationApi
@Composable
fun PostDetailsComponent(
    navigationManager: NavigationManager,
    viewModel: PostDetailsViewModel = hiltViewModel(),
    postId: String
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
                PostDetailsContent(
                    uiState as UiDataState.Success<PostDetailsData>,
                    navigationManager
                )
            }
        }
        viewModel.setStateForEvent(PostDetailsEvent.FetchPost(postId))
    }
}

@ExperimentalFoundationApi
@Composable
fun PostDetailsContent(
    uiState: UiDataState.Success<PostDetailsData>,
    navigationManager: NavigationManager
) {
    when (uiState.data) {
        is PostDetailsData.PostDetailsDataModel -> {
            uiState.data.post?.let { post ->
                PostDetailsContentComponent(
                    post = post,
                    navigationManager = navigationManager
                )
            }

        }
    }
}

@ExperimentalFoundationApi
@Composable
fun PostDetailsContentComponent(
    post: PostDetailsData.PostData,
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
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                modifier = Modifier
                    .padding(16.dp)
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .align(Alignment.CenterHorizontally),
                painter = rememberImagePainter(data = post.thumbnail, builder = {
                    crossfade(true)
                }),
                contentDescription = ""
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = post.name,
                style = MaterialTheme.typography.h4
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = post.description,
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.comments, post.commentCount.toString()),
                style = MaterialTheme.typography.caption
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.upvotes, post.votesCount.toString()),
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.makers),
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(4.dp))
            MakerListComponent(makers = post.makers, navigationManager = navigationManager)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.hunter),
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Image(
                modifier = Modifier
                    .size(96.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        postDetailsNavigationDelegate(
                            navigationManager = navigationManager,
                            navigationItem = NavigationDirections.profile.apply {
                                addArg(NavigationDirections.ARG_USER_ID, post.hunter.id)
                            }
                        )
                    },
                painter = rememberImagePainter(data = post.hunter.profileImage, builder = {
                    transformations(CircleCropTransformation())
                }),
                contentDescription = ""
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.media),
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp), mainAxisAlignment = FlowMainAxisAlignment.SpaceEvenly, mainAxisSpacing = 4.dp, crossAxisSpacing = 8.dp) {
                repeat(post.media.size) {
                    MediaGridComponent(post.media[it])
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun MediaGridComponent(media: PostDetailsData.MediaData) {
    Box(modifier = Modifier.size(100.dp), contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
            painter = rememberImagePainter(data = media.url, builder = {
                crossfade(true)
            }),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
    }

}

@Composable
fun MakerListComponent(
    makers: List<PostDetailsData.MakersData>,
    navigationManager: NavigationManager
) {
    LazyRow(
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(makers) { maker ->
            MakerComponent(maker = maker, navigationManager = navigationManager)
        }
    }
}

@Composable
fun MakerComponent(maker: PostDetailsData.MakersData, navigationManager: NavigationManager) {
    Surface(
        modifier = Modifier
            .size(96.dp)
            .clickable {
                postDetailsNavigationDelegate(
                    navigationManager = navigationManager,
                    navigationItem = NavigationDirections.profile.apply {
                        addArg(NavigationDirections.ARG_USER_ID, maker.id)
                    }
                )
            },
        shape = CircleShape
    ) {
        Column {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberImagePainter(data = maker.profileImage, builder = {
                    transformations(CircleCropTransformation())
                }),
                contentDescription = ""
            )
        }
    }
}

fun postDetailsNavigationDelegate(navigationManager: NavigationManager, navigationItem: NavigationItem) {
    navigationManager.navigate(navigationItem)
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MarkersTheme {

    }
}