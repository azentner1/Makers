package com.the.markers.features.profile.model

import com.the.markers.features.home.model.HomeData

sealed class ProfileData {

    data class ProfileDataModel(
        val profile: Profile?
    ) : ProfileData()

    data class Profile(
        val id: String,
        val coverImage: String,
        val username: String,
        val isMaker: Boolean,
        val headline: String,
        val name: String,
        val profileImage: String,
        val votedPosts: List<VotedPostData>
    )

    data class VotedPostData(
        val id: String,
        val name: String = "",
        val commentCount: Int = 0,
        val votesCount: Int = 0,
        val tagline: String = "",
        val hunter: ProfileHunterData,
        val thumbnail: String
    )

    data class ProfileHunterData(
        val id: String,
        val username: String,
    )
}