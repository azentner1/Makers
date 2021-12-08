package com.the.markers.features.home.datasource

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.api.toJson
import com.apollographql.apollo.exception.ApolloException
import com.the.markers.base.error.ErrorEntity
import com.the.markers.base.error.ErrorHandlerImpl
import com.the.markers.base.model.DataResult
import com.the.markers.features.home.mapper.HomeDataMapper
import com.the.markers.features.home.model.HomeData
import queries.GetPostsQuery
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class HomeDataSourceImpl(
    private val apolloClient: ApolloClient,
    private val homeDataMapper: HomeDataMapper,
    private val errorEntity: ErrorHandlerImpl
) : HomeDataSource {

    override suspend fun fetchPosts(): DataResult<HomeData> {
        return suspendCoroutine { data->
            apolloClient.query(GetPostsQuery(postedAfter = "2021-12-07T00:00:00Z".toInput())).enqueue(object : ApolloCall.Callback<GetPostsQuery.Data>() {
                override fun onResponse(response: Response<GetPostsQuery.Data>) {
                    when {
                        response.errors == null  || response.errors?.isEmpty() == true -> {
                            val json = response.data?.toJson()
                            data.resume(DataResult.Success(homeDataMapper.mapFromEntity(response.data)))
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