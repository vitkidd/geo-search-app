package com.github.vitkidd.geosearch

import com.github.vitkidd.geosearch.feature.data.dto.PhotoDto
import com.github.vitkidd.geosearch.feature.data.dto.PhotosDto
import com.github.vitkidd.geosearch.feature.data.dto.SearchResultDto
import com.github.vitkidd.geosearch.feature.domain.entity.PhotoEntity
import com.github.vitkidd.geosearch.feature.domain.entity.RegionEntity
import com.github.vitkidd.geosearch.feature.domain.entity.SearchEntity
import com.github.vitkidd.geosearch.feature.presentation.model.PhotoModel
import com.github.vitkidd.geosearch.feature.presentation.model.RegionModel

fun searchResultDto(): SearchResultDto {
    return SearchResultDto(
        photos = PhotosDto(
            page = 0,
            pages = 0,
            perpage = 0,
            total = "",
            photo = listOf(
                PhotoDto(
                    id = "17552416733",
                    server = "8842",
                    secret = "eac04444a8",
                    title = "Let's ride",
                    latitude = "52.384748",
                    longitude = "4.636037",
                    tags = "classic cars"
                ),
                PhotoDto(
                    id = "47052926504",
                    server = "65535",
                    secret = "f2131e1c2b",
                    title = "Haarlem",
                    latitude = "52.376582",
                    longitude = "4.633676",
                    tags = "haarlem vintage"
                )
            )
        )
    )
}

fun photoEntities(): List<PhotoEntity> {
    return listOf(
        PhotoEntity(
            lat = 52.384748,
            lon = 4.636037,
            urlSmall = "https://live.staticflickr.com/8842/17552416733_eac04444a8_s.jpg",
            urlMedium = "https://live.staticflickr.com/8842/17552416733_eac04444a8_z.jpg",
            title = "Let's ride",
            tags = "classic, cars"
        ),
        PhotoEntity(
            lat = 52.376582,
            lon = 4.633676,
            urlSmall = "https://live.staticflickr.com/65535/47052926504_f2131e1c2b_s.jpg",
            urlMedium = "https://live.staticflickr.com/65535/47052926504_f2131e1c2b_z.jpg",
            title = "Haarlem",
            tags = "haarlem, vintage"
        )
    )
}

fun photoModels(): List<PhotoModel> {
    return listOf(
        PhotoModel(
            lat = 52.384748,
            lon = 4.636037,
            urlSmall = "https://live.staticflickr.com/8842/17552416733_eac04444a8_s.jpg",
            urlMedium = "https://live.staticflickr.com/8842/17552416733_eac04444a8_z.jpg",
            title = "Let's ride",
            tags = "classic, cars"
        ),
        PhotoModel(
            lat = 52.376582,
            lon = 4.633676,
            urlSmall = "https://live.staticflickr.com/65535/47052926504_f2131e1c2b_s.jpg",
            urlMedium = "https://live.staticflickr.com/65535/47052926504_f2131e1c2b_z.jpg",
            title = "Haarlem",
            tags = "haarlem, vintage"
        )
    )
}

fun searchEntity(): SearchEntity {
    return SearchEntity(
        query = "motorcycle",
        regionModel = RegionEntity(
            minLongitude = 4.63144775480032,
            minLatitude = 52.376537420804254,
            maxLongitude = 4.640275910496712,
            maxLatitude = 52.38637202434913
        )
    )
}

fun regionModel(): RegionModel {
    return RegionModel(
        minLongitude = 4.63144775480032,
        minLatitude = 52.376537420804254,
        maxLongitude = 4.640275910496712,
        maxLatitude = 52.38637202434913
    )
}