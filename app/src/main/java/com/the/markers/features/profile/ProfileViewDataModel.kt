package com.the.markers.features.profile

import com.the.markers.base.model.DataResult
import com.the.markers.base.viewmodel.BaseViewModel
import com.the.markers.features.profile.model.ProfileData
import com.the.markers.features.profile.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewDataModel @Inject constructor(
    private val profileRepository: ProfileRepository
): BaseViewModel<ProfileData, ProfileEvent>() {

    override suspend fun doActionForEvent(event: ProfileEvent): DataResult<ProfileData> {
        when (event) {
            is ProfileEvent.FetchProfile -> {
                return profileRepository.fetchProfile(event.userId)
            }
        }
    }
}

sealed class ProfileEvent {
    data class FetchProfile(val userId: String): ProfileEvent()
}