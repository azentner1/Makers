package com.the.markers.features.profile.mapper

import com.the.markers.base.mapper.EntityMapper
import com.the.markers.features.profile.model.ProfileData
import queries.UserQuery
import javax.inject.Inject

class ProfileDataMapper @Inject constructor() : EntityMapper<UserQuery.Data?, ProfileData> {

    override fun mapFromEntity(entity: UserQuery.Data?): ProfileData {
        return ProfileData.ProfileDataModel(
            mapProfile(entity)
        )
    }

    private fun mapProfile(entity: UserQuery.Data?): ProfileData.Profile? {
        return entity?.user?.let {
            ProfileData.Profile(
                id = it.id,
                coverImage = it.coverImage ?: "",
                username = it.username,
                isMaker = it.isMaker,
                headline = it.headline ?: "",
                name = it.name,
                profileImage = it.profileImage ?: "",
                votedPosts = mapVotedPosts(it.votedPosts)
            )
        }
    }

    private fun mapVotedPosts(votedPosts: UserQuery.VotedPosts): List<ProfileData.VotedPostData> {
        return votedPosts.edges.map { 
            val node = it.node
            ProfileData.VotedPostData(
                id = node.id,
                name = node.name,
                commentCount = node.commentsCount,
                votesCount = node.votesCount,
                tagline = node.tagline,
                thumbnail = node.thumbnail?.url ?: "",
                hunter = mapHunterData(node.user)
            )
        }
    }

    private fun mapHunterData(user: UserQuery.User1): ProfileData.ProfileHunterData {
        return ProfileData.ProfileHunterData(
            id = user.id,
            username = user.username
        )
    }

    override fun mapToEntity(domainModel: ProfileData): UserQuery.Data {
        TODO("Not yet implemented")
    }

}