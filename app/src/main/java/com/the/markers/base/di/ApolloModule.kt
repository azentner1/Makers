package com.the.markers.base.di

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import com.the.markers.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier

@Module
@InstallIn(SingletonComponent::class)
object ApolloModule {

    private const val AUTHORIZATION_HEADER = "Authorization"

    @Singleton
    @Provides
    fun provideApollo(@ApplicationContext appContext: Context): ApolloClient {
        val okHttpClient = OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS)
            .cache(Cache(appContext.cacheDir, 1024 * 1024 * 10))
            .addInterceptor { chain ->
                val request =
                    chain.request().newBuilder().addHeader(AUTHORIZATION_HEADER, BuildConfig.TOKEN)
                        .build();
                chain.proceed(request);
            }
            .hostnameVerifier(HostnameVerifier { _, _ -> true })
        val cacheFactory = LruNormalizedCacheFactory(
            EvictionPolicy.builder().maxSizeBytes(10 * 1024 * 1024).build()
        )
        return ApolloClient
            .builder()
            .serverUrl(BuildConfig.BASE_URL)
            .normalizedCache(cacheFactory)
            .okHttpClient(okHttpClient.build())
            .build()
    }
}