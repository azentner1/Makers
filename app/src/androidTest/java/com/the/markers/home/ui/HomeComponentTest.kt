package com.the.markers.home.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.the.markers.MainActivity
import com.the.markers.base.test.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime

@ExperimentalFoundationApi
@ExperimentalTime
@HiltAndroidTest
class HomeComponentTest {

    private lateinit var latch: CountDownLatch

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeRule = createAndroidComposeRule(MainActivity::class.java)

    private val waitingTime: Long = 2
    private val waitingTimeUnit: TimeUnit = TimeUnit.SECONDS

    @Before
    fun setup() {
        hiltTestRule.inject()
    }

    @Test
    fun homeStartupTest() {
        latch = CountDownLatch(2)
        val progressBar = composeRule.onNode(hasTestTag(TestTags.TAG_LOADING_INDICATOR),true)
        latch.await(waitingTime, waitingTimeUnit)
        progressBar.assertIsDisplayed()
        latch.countDown()
        latch.await(waitingTime, waitingTimeUnit)
        val lazyList = composeRule.onNode(hasTestTag(TestTags.TAG_HOME_POST_LIST))
        lazyList.assertIsDisplayed()
        latch.countDown()
    }
}