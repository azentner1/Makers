package com.the.markers.base.viewmodel

import androidx.lifecycle.*
import com.the.markers.base.model.DataResult
import com.the.markers.base.model.UiDataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel<Model, Event> : ViewModel() {

    private var job: Job? = null
    private val dataStateMutableLiveData: MutableLiveData<UiDataState<Model>> = MutableLiveData(UiDataState.Idle)

    val dataState: LiveData<UiDataState<Model>> get() = dataStateMutableLiveData


    fun setStateForEvent(event: Event) {
        job = viewModelScope.launch {
            if (dataStateMutableLiveData.value == UiDataState.Idle) {
                dataStateMutableLiveData.value = UiDataState.Loading
            }
            withContext(Dispatchers.IO) {
                val dataResult = doActionForEvent(event)
                withContext(Dispatchers.Main) {
                    when (dataResult) {
                        is DataResult.Success -> {
                            dataStateMutableLiveData.value = UiDataState.Success(dataResult.data)
                        }
                        is DataResult.Error -> {
                            dataStateMutableLiveData.value = UiDataState.Error(dataResult.error)
                        }
                    }
                }
            }
        }
        job?.start()
    }

    protected abstract suspend fun doActionForEvent(event: Event): DataResult<Model>
}