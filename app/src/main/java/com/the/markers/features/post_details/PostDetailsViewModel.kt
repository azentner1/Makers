package com.the.markers.features.post_details

import com.the.markers.base.model.DataResult
import com.the.markers.base.viewmodel.BaseViewModel
import com.the.markers.features.post_details.model.PostDetailsData
import com.the.markers.features.post_details.repository.PostDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val postDetailsRepository: PostDetailsRepository
): BaseViewModel<PostDetailsData, PostDetailsEvent>()  {

    override suspend fun doActionForEvent(event: PostDetailsEvent): DataResult<PostDetailsData> {
        when (event) {
            is PostDetailsEvent.FetchPost -> {
               return postDetailsRepository.fetchPost(event.postId)
            }
        }
    }
}

sealed class PostDetailsEvent {
    data class FetchPost(val postId: String): PostDetailsEvent()
}