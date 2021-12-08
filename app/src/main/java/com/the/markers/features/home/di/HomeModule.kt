package com.the.markers.features.home.di

import com.apollographql.apollo.ApolloClient
import com.the.markers.base.error.ErrorHandlerImpl
import com.the.markers.features.home.datasource.HomeDataSource
import com.the.markers.features.home.datasource.HomeDataSourceImpl
import com.the.markers.features.home.mapper.HomeDataMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Singleton
    @Provides
    fun provideHomeDataSource(
        apolloClient: ApolloClient,
        homeDataMapper: HomeDataMapper
    ): HomeDataSource = HomeDataSourceImpl(
        apolloClient = apolloClient,
        homeDataMapper = homeDataMapper,
        errorEntity = ErrorHandlerImpl()
    )
}