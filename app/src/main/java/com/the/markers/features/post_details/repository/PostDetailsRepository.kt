package com.the.markers.features.post_details.repository

import com.the.markers.base.model.DataResult
import com.the.markers.features.post_details.datasource.PostDetailsDataSource
import com.the.markers.features.post_details.model.PostDetailsData
import javax.inject.Inject

class PostDetailsRepository @Inject constructor(
    private val postDetailsDataSource: PostDetailsDataSource
) {

    suspend fun fetchPost(postId: String): DataResult<PostDetailsData> {
        return postDetailsDataSource.fetchPost(postId)
    }

}