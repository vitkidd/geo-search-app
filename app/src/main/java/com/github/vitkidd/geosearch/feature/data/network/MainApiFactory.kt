package com.github.vitkidd.geosearch.feature.data.network

import com.github.vitkidd.geosearch.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CONNECT_TIMEOUT = 5L
private const val WRITE_TIMEOUT = 20L
private const val READ_TIMEOUT = 20L
private const val BASE_URL = "https://www.flickr.com/services/rest/"
private const val QUERY_PARAM_API_KEY = "api_key"
private const val QUERY_PARAM_FORMAT_NAME = "format"
private const val QUERY_PARAM_FORMAT_VALUE = "json"
private const val QUERY_PARAM_NO_JSON_NAME = "nojsoncallback"
private const val QUERY_PARAM_NO_JSON_VALUE = "1"

fun createMainApi(): MainApi {
    val queryInterceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {

            val original = chain.request()
            val originalHttpUrl = original.url

            val url = originalHttpUrl
                .newBuilder()
                .addQueryParameter(QUERY_PARAM_API_KEY, BuildConfig.FLICKR_API_KEY)
                .addQueryParameter(QUERY_PARAM_FORMAT_NAME, QUERY_PARAM_FORMAT_VALUE)
                .addQueryParameter(QUERY_PARAM_NO_JSON_NAME, QUERY_PARAM_NO_JSON_VALUE)
                .build()

            val requestBuilder = original
                .newBuilder()
                .url(url)

            val request = requestBuilder.build()

            return chain.proceed(request)
        }
    }

    val okHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        addInterceptor(queryInterceptor)
        addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
    }.build()

    return Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .build()
        .create(MainApi::class.java)
}