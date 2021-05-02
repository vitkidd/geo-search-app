package com.github.vitkidd.geosearch.feature.domain.entity

data class PhotoEntity(
    val lat: Double,
    val lon: Double,
    val urlSmall: String,
    val urlMedium: String,
    val title: String,
    val tags: String
)
