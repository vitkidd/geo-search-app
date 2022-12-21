package com.github.vitkidd.geosearch.feature.presentation.ui

import android.content.Context
import com.bumptech.glide.Glide
import com.github.vitkidd.geosearch.feature.presentation.model.PhotoModel
import com.github.vitkidd.geosearch.feature.presentation.model.RegionModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

internal class MapManager(
    private val map: GoogleMap,
    private val regionChanged: (RegionModel) -> Unit,
    private val onMarkerClicked: (PhotoModel) -> Unit,
) {

    private val targets = mutableListOf<MarkerTarget>()

    init {
        map.setOnCameraIdleListener(::onRegionChanged)
        map.setOnMarkerClickListener(::onMarkerClicked)
    }

    fun updateMarkers(context: Context, photos: List<PhotoModel>) {
        clearMap(context)
        targets.addAll(photos.map { MarkerTarget(map, it) })
        targets.forEach { loadMarkerIcon(context, it) }
    }

    fun clearMap(context: Context) {
        targets.forEach { Glide.with(context).clear(it) }
        targets.clear()
        map.clear()
    }

    private fun loadMarkerIcon(context: Context, target: MarkerTarget) {
        Glide.with(context)
            .asBitmap()
            .load(target.photoModel.urlSmall)
            .into(target)
    }

    private fun onRegionChanged() = with(map.projection.visibleRegion) {
        regionChanged.invoke(
            RegionModel(
                minLongitude = nearLeft.longitude,
                minLatitude = nearLeft.latitude,
                maxLongitude = farRight.longitude,
                maxLatitude = farRight.latitude,
            )
        )
    }

    private fun onMarkerClicked(marker: Marker): Boolean {
        (marker.tag as? PhotoModel)?.let {
            onMarkerClicked.invoke(it)
        }

        return true
    }
}