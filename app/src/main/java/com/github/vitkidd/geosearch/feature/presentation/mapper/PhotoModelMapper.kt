package com.github.vitkidd.geosearch.feature.presentation.mapper

import com.github.vitkidd.geosearch.feature.domain.entity.PhotoEntity
import com.github.vitkidd.geosearch.feature.presentation.model.PhotoModel

class PhotoModelMapper {

    fun map(photoEntity: PhotoEntity) = PhotoModel(
        lat = photoEntity.lat,
        lon = photoEntity.lon,
        urlSmall = photoEntity.urlSmall,
        urlMedium = photoEntity.urlMedium,
        title = photoEntity.title,
        tags = photoEntity.tags
    )
}