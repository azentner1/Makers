package com.the.markers.features.home.model

sealed class HomeData {

    data class PostDataModel(
        val postList: List<PostData>
    ) : HomeData()

    data class PostData(
        val id: String,
        val name: String = "",
        val commentCount: Int = 0,
        val votesCount: Int = 0,
        val tagline: String = "",
        val hunter: UserData,
        val image: String
    )

    data class UserData(
        val id: String,
        val coverImage: String,
        val username: String,
        val isMaker: Boolean,
        val headline: String,
        val name: String,
        val profileImage: String
    )
}
