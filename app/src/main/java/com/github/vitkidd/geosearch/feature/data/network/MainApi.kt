package com.github.vitkidd.geosearch.feature.data.network

import com.github.vitkidd.geosearch.feature.data.dto.SearchResultDto
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface MainApi {

    @GET("?method=flickr.photos.search&extras=geo,description,tags&per_page=10")
    fun search(
        @Query("text") text: String,
        @Query("bbox") region: String,
    ) : Observable<SearchResultDto>

    companion object Factory {

        fun create(): MainApi {
            val queryInterceptor = object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original = chain.request()
                    val originalHttpUrl = original.url

                    val url = originalHttpUrl
                        .newBuilder()
                        .addQueryParameter("api_key", "6a64cfe6e6ba12e4629b0f11031a094f")
                        .addQueryParameter("format", "json")
                        .addQueryParameter("nojsoncallback", "1")
                        .build()

                    val requestBuilder = original
                        .newBuilder()
                        .url(url)

                    val request = requestBuilder.build()

                    return chain.proceed(request)
                }
            }

            val okHttpClient = OkHttpClient.Builder().apply {
                connectTimeout(5, TimeUnit.SECONDS)
                writeTimeout(20, TimeUnit.SECONDS)
                readTimeout(20, TimeUnit.SECONDS)
                addInterceptor(queryInterceptor)
                addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            }.build()

            return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl("https://www.flickr.com/services/rest/")
                .build()
                .create(MainApi::class.java)
        }
    }
}