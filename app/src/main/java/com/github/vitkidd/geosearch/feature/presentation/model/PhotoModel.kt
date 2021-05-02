package com.github.vitkidd.geosearch.feature.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoModel(
    val lat: Double,
    val lon: Double,
    val urlSmall: String,
    val urlMedium: String,
    val title: String,
    val tags: String
) : Parcelable
