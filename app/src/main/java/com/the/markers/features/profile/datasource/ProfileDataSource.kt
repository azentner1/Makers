package com.the.markers.features.profile.datasource

import com.the.markers.base.model.DataResult
import com.the.markers.features.profile.model.ProfileData

interface ProfileDataSource {
    suspend fun fetchProfile(userId: String): DataResult<ProfileData>
}