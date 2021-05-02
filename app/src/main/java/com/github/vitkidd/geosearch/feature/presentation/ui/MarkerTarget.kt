package com.github.vitkidd.geosearch.feature.presentation.ui

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.github.vitkidd.geosearch.feature.presentation.model.PhotoModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MarkerTarget(
    var map: GoogleMap?,
    var photoModel: PhotoModel
) : CustomTarget<Bitmap>() {

    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
        val options = MarkerOptions()
            .position(LatLng(photoModel.lat, photoModel.lon))
            .icon(BitmapDescriptorFactory.fromBitmap(resource))
        val marker = map?.addMarker(options)
        marker?.tag = photoModel
    }

    override fun onLoadCleared(placeholder: Drawable?) {
        map = null
    }
}