package com.github.vitkidd.geosearch.feature.data.repo

import com.github.vitkidd.geosearch.feature.data.dto.PhotoDto
import com.github.vitkidd.geosearch.feature.data.network.MainApi
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class MainRepositoryImpl(
    private val mainApi: MainApi
) : MainRepository {

    override fun searchPhotos(query: String, region: String): Observable<List<PhotoDto>> {
        return mainApi.search(query, region).map { it.photos.photos }.subscribeOn(Schedulers.io())
    }
}