package com.the.markers.home

import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.the.markers.base.error.ErrorEntity
import com.the.markers.base.model.DataResult
import com.the.markers.base.model.UiDataState
import com.the.markers.features.home.HomeEvent
import com.the.markers.features.home.HomeViewModel
import com.the.markers.features.home.datasource.HomeDataSource
import com.the.markers.features.home.datasource.HomeDataSourceImpl
import com.the.markers.features.home.model.HomeData
import com.the.markers.features.home.repository.HomeRepository
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.launch
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import javax.inject.Inject

@HiltAndroidTest
class HomeViewModelTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    private val dataModel = HomeData.PostDataModel(listOf())
    private val dataSource = MockupHomeDataSource(
        isSuccessful = true,
        dataModel = dataModel,
        errorEntity = ErrorEntity.Network
    )

    private val waitingTime: Long = 2
    private val waitingTimeUnit: TimeUnit = TimeUnit.SECONDS
    private lateinit var latch: CountDownLatch

    @Inject
    lateinit var repo: HomeRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun init() {
        hiltRule.inject()
        viewModel = HomeViewModel(repo)
        latch = CountDownLatch(2)
    }

    @Test
    fun getHomeDataSuccessfully() {
        runCheck(object : Observer<UiDataState<HomeData>> {
            override fun onChanged(state: UiDataState<HomeData>) {
                if (latch.count == 2L) {
                    assertTrue(state == UiDataState.Idle)
                    latch.countDown()
                } else if (latch.count == 1L) {
                    assertTrue(state == UiDataState.Loading)
                    latch.countDown()
                } else {
                    assertTrue(state == UiDataState.Success(dataModel))
                    latch.countDown()
                    viewModel.dataState.removeObserver(this)
                }
            }
        })
    }

    @Test
    fun getHomeDataError() {
        dataSource.isSuccessful = false
        runCheck(object : Observer<UiDataState<HomeData>> {
            override fun onChanged(state: UiDataState<HomeData>) {
                if (latch.count == 2L) {
                    assertTrue(state == UiDataState.Idle)
                    latch.countDown()
                } else if (latch.count == 1L) {
                    assertTrue(state == UiDataState.Loading)
                    latch.countDown()
                } else {
                    assertTrue(state == UiDataState.Success(dataModel))
                    latch.countDown()
                    viewModel.dataState.removeObserver(this)
                }
            }
        })
    }

    private fun runCheck(observer: Observer<UiDataState<HomeData>>) {
        viewModel.viewModelScope.launch {
            viewModel.dataState.observeForever(observer)
        }
        viewModel.setStateForEvent(HomeEvent.FetchPosts)
        if (!latch.await(waitingTime, waitingTimeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }
    }
}

private class MockupHomeDataSource(
    var isSuccessful: Boolean = true,
    val dataModel: HomeData.PostDataModel,
    val errorEntity: ErrorEntity = ErrorEntity.Network
) : HomeDataSource {

    override suspend fun fetchPosts(): DataResult<HomeData> {
        return if (isSuccessful) {
            DataResult.Success(dataModel)
        } else {
            DataResult.Error(errorEntity)
        }
    }
}