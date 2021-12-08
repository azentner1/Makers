package com.the.markers.features.home

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.the.markers.base.model.DataResult
import com.the.markers.base.model.UiDataState
import com.the.markers.base.viewmodel.BaseViewModel
import com.the.markers.features.home.model.HomeData
import com.the.markers.features.home.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : BaseViewModel<HomeData, HomeEvent>() {

    override suspend fun doActionForEvent(event: HomeEvent): DataResult<HomeData> {
        when (event) {
            is HomeEvent.FetchPosts -> {
                return homeRepository.fetchPosts()
            }
        }
    }
}

sealed class HomeEvent {
    object FetchPosts : HomeEvent()
}