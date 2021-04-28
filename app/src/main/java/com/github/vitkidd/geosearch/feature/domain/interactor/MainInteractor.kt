package com.github.vitkidd.geosearch.feature.domain.interactor

import com.github.vitkidd.geosearch.feature.domain.entity.PhotoEntity
import com.github.vitkidd.geosearch.feature.domain.entity.SearchEntity
import io.reactivex.Observable

interface MainInteractor {
    fun searchPhotos(searchEntity: SearchEntity): Observable<List<PhotoEntity>>
}