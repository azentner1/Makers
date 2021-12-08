package com.the.markers.features.profile.repository

import com.the.markers.base.model.DataResult
import com.the.markers.features.profile.datasource.ProfileDataSource
import com.the.markers.features.profile.model.ProfileData
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val profileDataSource: ProfileDataSource
) {

    suspend fun fetchProfile(userId: String): DataResult<ProfileData> {
        return profileDataSource.fetchProfile(userId)
    }
}