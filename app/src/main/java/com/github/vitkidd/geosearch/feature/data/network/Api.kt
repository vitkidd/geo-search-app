package com.github.vitkidd.geosearch.feature.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface Api {

    @GET
    fun search(
        @Query("method") method: String,
        @Query("api_key") apiKey: String,
        @Query("ormat") format: String,
        @Query("nojsoncallback") noJsonCallback: Int,
        @Query("extras") extras: String,
        @Query("text") text: String
    )

    companion object Factory {
        fun create(): Api {
            val okHttpClient = OkHttpClient.Builder().apply {
                connectTimeout(5, TimeUnit.SECONDS)
                writeTimeout(20, TimeUnit.SECONDS)
                readTimeout(20, TimeUnit.SECONDS)
            }.build()

            return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl("https://www.flickr.com/services/rest/")
                .build()
                .create(Api::class.java)
        }
    }
}