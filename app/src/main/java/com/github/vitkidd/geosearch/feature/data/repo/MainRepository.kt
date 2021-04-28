package com.github.vitkidd.geosearch.feature.data.repo

import com.github.vitkidd.geosearch.feature.data.dto.PhotoDto
import io.reactivex.Observable

interface MainRepository {
    fun searchPhotos(query: String, region: String): Observable<List<PhotoDto>>
}