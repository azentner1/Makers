package com.the.markers.features.post_details.model

import com.the.markers.features.home.model.HomeData

sealed class PostDetailsData {

    data class PostDetailsDataModel(
        val post: PostData?
    ) : PostDetailsData()

    data class PostData(
        val id: String,
        val name: String = "",
        val slug: String = "",
        val createdAt: Any,
        val description: String = "",
        val commentCount: Int = 0,
        val votesCount: Int = 0,
        val tagline: String = "",
        val thumbnail: String = "",
        val makers: List<MakersData>,
        val media: List<MediaData>,
        val hunter: HunterData
    )

    data class MakersData(
        val id: String,
        val coverImage: String,
        val username: String,
        val isMaker: Boolean,
        val headline: String,
        val name: String,
        val profileImage: String
    )

    data class MediaData(
        val type: String,
        val url: String
    )

    data class HunterData(
        val id: String,
        val username: String,
        val profileImage: String
    )
}