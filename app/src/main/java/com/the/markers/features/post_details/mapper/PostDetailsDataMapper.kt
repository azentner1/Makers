package com.the.markers.features.post_details.mapper

import com.the.markers.base.mapper.EntityMapper
import com.the.markers.features.post_details.model.PostDetailsData
import queries.PostQuery
import javax.inject.Inject

class PostDetailsDataMapper @Inject constructor(): EntityMapper<PostQuery.Data?, PostDetailsData> {
    
    override fun mapFromEntity(entity: PostQuery.Data?): PostDetailsData {
        return PostDetailsData.PostDetailsDataModel(
            post = mapPostDetailsData(entity)
        )
    }

    private fun mapPostDetailsData(entity: PostQuery.Data?): PostDetailsData.PostData? {
        return entity?.post?.let { post->
            PostDetailsData.PostData(
                id = post.id,
                name = post.name,
                slug = post.slug,
                createdAt = post.createdAt,
                description = post.description ?: "",
                commentCount = post.commentsCount,
                votesCount = post.votesCount,
                makers = mapMakersList(post.makers),
                tagline = post.tagline,
                thumbnail = post.thumbnail?.url ?: "",
                media = mapMediaList(post.media),
                hunter = mapHunterData(post.user)
            )
        }
    }

    private fun mapHunterData(hunter: PostQuery.User): PostDetailsData.HunterData {
        return PostDetailsData.HunterData(
            id = hunter.id,
            username = hunter.username,
            profileImage = hunter.profileImage ?: ""
        )
    }

    private fun mapMediaList(mediaList: List<PostQuery.Medium>): List<PostDetailsData.MediaData> {
        return mediaList.map { media ->
            PostDetailsData.MediaData(
                type = media.type,
                url = media.url
            )
        }.filter { it.type == "image" }
    }

    private fun mapMakersList(makers: List<PostQuery.Maker>): List<PostDetailsData.MakersData> {
        return makers.map {
            PostDetailsData.MakersData(
                id = it.id,
                coverImage = it.coverImage ?: "",
                username = it.username,
                isMaker = it.isMaker,
                headline = it.headline ?: "",
                name = it.name,
                profileImage = it.profileImage ?: ""
            )
        }
    }
    
    override fun mapToEntity(domainModel: PostDetailsData): PostQuery.Data? {
        TODO("Not yet implemented")
    }

}