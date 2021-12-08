package com.the.markers.features.post_details.datasource

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.exception.ApolloException
import com.the.markers.base.error.ErrorEntity
import com.the.markers.base.error.ErrorHandlerImpl
import com.the.markers.base.model.DataResult
import com.the.markers.features.home.mapper.HomeDataMapper
import com.the.markers.features.post_details.mapper.PostDetailsDataMapper
import com.the.markers.features.post_details.model.PostDetailsData
import queries.GetPostsQuery
import queries.PostQuery
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PostDetailsDataSourceImpl(
    private val apolloClient: ApolloClient,
    private val postDetailsDataMapper: PostDetailsDataMapper,
    private val errorEntity: ErrorHandlerImpl
): PostDetailsDataSource {

    override suspend fun fetchPost(postId: String): DataResult<PostDetailsData> {
        return suspendCoroutine { data->
            apolloClient.query(PostQuery(id = postId.toInput())).enqueue(object : ApolloCall.Callback<PostQuery.Data>() {
                override fun onResponse(response: Response<PostQuery.Data>) {
                    when {
                        response.errors == null  || response.errors?.isEmpty() == true -> {
                            data.resume(DataResult.Success(postDetailsDataMapper.mapFromEntity(response.data)))
                        }
                        else -> {
                            data.resume(DataResult.Error(ErrorEntity.AccessDenied))
                        }
                    }
                }

                override fun onFailure(e: ApolloException) {
                    data.resume(DataResult.Error(ErrorEntity.Unknown))
                }

            })
        }
    }
}