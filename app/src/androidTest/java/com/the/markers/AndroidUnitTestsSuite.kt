package com.the.markers

import com.the.markers.home.integration.HomeDataSourceTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

@ExperimentalCoroutinesApi
@RunWith(Suite::class)
@Suite.SuiteClasses(
    HomeDataSourceTest::class

)
class AndroidUnitTestsSuite