package com.the.markers.features.post_details.datasource

import com.the.markers.base.model.DataResult
import com.the.markers.features.post_details.model.PostDetailsData

interface PostDetailsDataSource {
    suspend fun fetchPost(postId: String): DataResult<PostDetailsData>
}