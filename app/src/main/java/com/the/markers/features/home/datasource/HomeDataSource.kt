package com.the.markers.features.home.datasource

import com.the.markers.base.model.DataResult
import com.the.markers.features.home.model.HomeData

interface HomeDataSource {
    suspend fun fetchPosts() : DataResult<HomeData>
}