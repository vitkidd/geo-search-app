package com.github.vitkidd.geosearch.feature.data.dto

data class SearchResultDto(
    val photos: PhotosDto
)

data class PhotosDto(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val total: String,
    val photos: List<PhotoDto>
)