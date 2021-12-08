package com.the.markers.features.profile.di

import com.apollographql.apollo.ApolloClient
import com.the.markers.base.error.ErrorHandlerImpl
import com.the.markers.features.home.datasource.HomeDataSource
import com.the.markers.features.home.datasource.HomeDataSourceImpl
import com.the.markers.features.home.mapper.HomeDataMapper
import com.the.markers.features.post_details.datasource.PostDetailsDataSource
import com.the.markers.features.post_details.datasource.PostDetailsDataSourceImpl
import com.the.markers.features.post_details.mapper.PostDetailsDataMapper
import com.the.markers.features.profile.datasource.ProfileDataSource
import com.the.markers.features.profile.datasource.ProfileDataSourceImpl
import com.the.markers.features.profile.mapper.ProfileDataMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Singleton
    @Provides
    fun provideProfileDataSource(
        apolloClient: ApolloClient,
        profileDataMapper: ProfileDataMapper,
    ): ProfileDataSource = ProfileDataSourceImpl(
        apolloClient = apolloClient,
        profileDataMapper = profileDataMapper,
        errorEntity = ErrorHandlerImpl()
    )
}