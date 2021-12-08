package com.the.markers.base.di

import com.the.markers.base.navigation.NavigationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationManagerModule {

    @Singleton
    @Provides
    fun provideNavigationManager() = NavigationManager()
}