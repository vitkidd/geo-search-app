package com.github.vitkidd.geosearch.feature.data.dto

data class PhotoDto(
    val id: String,
    val server: String,
    val secret: String,
    val title: String,
    val latitude: String,
    val longitude: String,
    val tags: String
)
