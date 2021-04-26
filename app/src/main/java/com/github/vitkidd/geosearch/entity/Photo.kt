package com.github.vitkidd.geosearch.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    val id: String,
    val server: String,
    val secret: String,
    val title: String,
    val latitude: String,
    val longitude: String
) : Parcelable {

    val lat: Double
        get() = latitude.toDouble()

    val lon: Double
        get() = longitude.toDouble()
}
