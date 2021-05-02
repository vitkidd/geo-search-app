package com.github.vitkidd.geosearch.feature.domain.interactor

import com.github.vitkidd.geosearch.feature.domain.entity.PhotoEntity
import com.github.vitkidd.geosearch.feature.data.repo.MainRepository
import com.github.vitkidd.geosearch.feature.domain.entity.SearchEntity
import io.reactivex.Observable

class MainInteractorImpl(
    private val mainRepository: MainRepository
) : MainInteractor {

    override fun searchPhotos(searchEntity: SearchEntity): Observable<List<PhotoEntity>> {
        val region = searchEntity.regionModel.let {
            "${it.minLongitude},${it.minLatitude},${it.maxLongitude},${it.maxLatitude}"
        }

        return mainRepository
            .searchPhotos(searchEntity.query, region)
            .map {
                it.map { dto ->
                    PhotoEntity(
                        lat = dto.latitude.toDouble(),
                        lon = dto.longitude.toDouble(),
                        urlSmall = "https://live.staticflickr.com/${dto.server}/${dto.id}_${dto.secret}_s.jpg",
                        urlMedium = "https://live.staticflickr.com/${dto.server}/${dto.id}_${dto.secret}_z.jpg",
                        title = dto.title,
                        tags = dto.tags.replace(" ", ", ")
                    )
                }
            }
    }
}