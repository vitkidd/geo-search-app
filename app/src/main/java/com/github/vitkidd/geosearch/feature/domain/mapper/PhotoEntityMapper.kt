package com.github.vitkidd.geosearch.feature.domain.mapper

import com.github.vitkidd.geosearch.feature.data.dto.PhotoDto
import com.github.vitkidd.geosearch.feature.domain.entity.PhotoEntity

class PhotoEntityMapper {

    fun map(dto: PhotoDto): PhotoEntity {
        return PhotoEntity(
            lat = dto.latitude.toDouble(),
            lon = dto.longitude.toDouble(),
            urlSmall = "https://live.staticflickr.com/${dto.server}/${dto.id}_${dto.secret}_s.jpg",
            urlMedium = "https://live.staticflickr.com/${dto.server}/${dto.id}_${dto.secret}_z.jpg",
            title = dto.title,
            tags = dto.tags.replace(" ", ", ")
        )
    }
}