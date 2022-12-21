package com.github.vitkidd.geosearch.feature.domain.interactor

import com.github.vitkidd.geosearch.feature.domain.entity.PhotoEntity
import com.github.vitkidd.geosearch.feature.data.repo.MainRepository
import com.github.vitkidd.geosearch.feature.domain.entity.SearchEntity
import com.github.vitkidd.geosearch.feature.domain.mapper.PhotoEntityMapper
import io.reactivex.Observable

class MainInteractorImpl(
    private val mainRepository: MainRepository,
    private val photoEntityMapper: PhotoEntityMapper,
) : MainInteractor {

    override fun searchPhotos(searchEntity: SearchEntity): Observable<List<PhotoEntity>> {
        val region = searchEntity.regionModel.let {
            "${it.minLongitude},${it.minLatitude},${it.maxLongitude},${it.maxLatitude}"
        }

        return mainRepository
            .searchPhotos(searchEntity.query, region)
            .map { it.map(photoEntityMapper::map) }
    }
}