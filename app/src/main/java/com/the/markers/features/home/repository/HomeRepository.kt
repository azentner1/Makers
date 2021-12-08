package com.the.markers.features.home.repository

import com.the.markers.base.model.DataResult
import com.the.markers.features.home.datasource.HomeDataSource
import com.the.markers.features.home.model.HomeData
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeDataSource: HomeDataSource
) {

    suspend fun fetchPosts(): DataResult<HomeData> {
        return homeDataSource.fetchPosts()
    }
}