package com.github.vitkidd.geosearch.feature.data.network

import com.github.vitkidd.geosearch.feature.data.dto.SearchResultDto
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MainApi {

    @GET("?method=flickr.photos.search&extras=geo,description,tags&per_page=10")
    fun search(
        @Query("text") text: String,
        @Query("bbox") region: String,
    ) : Observable<SearchResultDto>
}