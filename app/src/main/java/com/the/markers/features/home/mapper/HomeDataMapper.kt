package com.the.markers.features.home.mapper

import com.the.markers.base.mapper.EntityMapper
import com.the.markers.features.home.model.HomeData
import queries.GetPostsQuery
import javax.inject.Inject

class HomeDataMapper @Inject constructor() : EntityMapper<GetPostsQuery.Data?, HomeData> {

    override fun mapFromEntity(entity: GetPostsQuery.Data?): HomeData {
        return HomeData.PostDataModel(
            postList = mapPostData(entity)
        )
    }

    private fun mapPostData(entity: GetPostsQuery.Data?): List<HomeData.PostData> {
        return entity?.posts?.edges?.map {
            val node = it.node
            HomeData.PostData(
                id = node.id,
                name = node.name,
                commentCount = node.commentsCount,
                votesCount = node.votesCount,
                hunter = mapHunterData(node.user),
                image = node.thumbnail?.url ?: "",
                tagline = node.tagline
            )
        } ?: listOf()
    }

    private fun mapHunterData(user: GetPostsQuery.User): HomeData.UserData {
        return HomeData.UserData(
            id = user.id,
            coverImage = user.coverImage ?: "",
            username = user.username,
            isMaker = user.isMaker,
            headline = user.headline ?: "",
            name = user.name,
            profileImage = user.profileImage ?: ""
        )
    }


    override fun mapToEntity(domainModel: HomeData): GetPostsQuery.Data? {
        TODO("Not yet implemented")
    }
}