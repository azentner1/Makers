package com.the.markers.features.post_details.di

import com.apollographql.apollo.ApolloClient
import com.the.markers.base.error.ErrorHandlerImpl
import com.the.markers.features.home.datasource.HomeDataSource
import com.the.markers.features.home.datasource.HomeDataSourceImpl
import com.the.markers.features.home.mapper.HomeDataMapper
import com.the.markers.features.post_details.datasource.PostDetailsDataSource
import com.the.markers.features.post_details.datasource.PostDetailsDataSourceImpl
import com.the.markers.features.post_details.mapper.PostDetailsDataMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostDetailsModule {

    @Singleton
    @Provides
    fun providePostDetailsDataSource(
        apolloClient: ApolloClient,
        postDetailsDataMapper: PostDetailsDataMapper
    ): PostDetailsDataSource = PostDetailsDataSourceImpl(
        apolloClient = apolloClient,
        postDetailsDataMapper = postDetailsDataMapper,
        errorEntity = ErrorHandlerImpl()
    )
}