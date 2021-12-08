package com.the.markers.home.integration

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.google.common.truth.Truth.assertThat
import com.the.markers.features.home.mapper.HomeDataMapper
import com.the.markers.features.home.model.HomeData
import com.the.markers.test_core.BaseTestClass
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Test
import queries.GetPostsQuery
import queries.PostQuery

typealias PostsServerData = GetPostsQuery.Data

class HomeDataSourceTest : BaseTestClass() {

    private val homeDataMapper = HomeDataMapper()

    @Test
    fun testSuccessfulResponseMapping() {
        //Response snapshot 08/12
        mockWebServer.enqueue(mockResponse(TEST_FILE_NAME))
        runBlocking {
            val response: Response<PostsServerData> = apolloClient.query(GetPostsQuery()).await()
            val dataMapperResponse = homeDataMapper.mapFromEntity(response.data!!)
            runAssertions(dataMapperResponse)
        }
    }

    private fun runAssertions(data: HomeData) {
        assertThat(data is HomeData.PostDataModel)
        val postList = (data as HomeData.PostDataModel).postList
        assertThat(postList).isNotEmpty()
        assertThat(postList.first().id).isEqualTo("322182")
        assertThat(postList[1].name).isEqualTo("Fathom")
    }

    companion object {
        private const val TEST_FILE_NAME = "HomeQueryResponse.json"
    }

}