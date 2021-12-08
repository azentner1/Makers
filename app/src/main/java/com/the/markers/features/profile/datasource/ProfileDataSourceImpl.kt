package com.the.markers.features.profile.datasource

import android.provider.ContactsContract
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.exception.ApolloException
import com.the.markers.base.error.ErrorEntity
import com.the.markers.base.error.ErrorHandlerImpl
import com.the.markers.base.model.DataResult
import com.the.markers.features.post_details.mapper.PostDetailsDataMapper
import com.the.markers.features.profile.mapper.ProfileDataMapper
import com.the.markers.features.profile.model.ProfileData
import queries.GetPostsQuery
import queries.UserQuery
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ProfileDataSourceImpl(
    private val apolloClient: ApolloClient,
    private val profileDataMapper: ProfileDataMapper,
    private val errorEntity: ErrorHandlerImpl
): ProfileDataSource {

    override suspend fun fetchProfile(userId: String): DataResult<ProfileData> {
        return suspendCoroutine { data->
            apolloClient.query(UserQuery(id = userId.toInput())).enqueue(object : ApolloCall.Callback<UserQuery.Data>() {
                override fun onResponse(response: Response<UserQuery.Data>) {
                    when {
                        response.errors == null  || response.errors?.isEmpty() == true -> {
                            data.resume(DataResult.Success(profileDataMapper.mapFromEntity(response.data)))
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