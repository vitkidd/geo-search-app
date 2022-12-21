package com.github.vitkidd.geosearch.feature.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.github.vitkidd.geosearch.R
import com.github.vitkidd.geosearch.databinding.FmtMainBinding
import com.github.vitkidd.geosearch.feature.di.DEFAULT_LATITUDE
import com.github.vitkidd.geosearch.feature.di.DEFAULT_LONGITUDE
import com.github.vitkidd.geosearch.feature.presentation.viewModel.MainViewState
import com.github.vitkidd.geosearch.feature.presentation.model.PhotoModel
import com.github.vitkidd.geosearch.feature.presentation.model.RegionModel
import com.github.vitkidd.geosearch.feature.presentation.viewModel.MainViewModel
import com.github.vitkidd.geosearch.feature.presentation.viewModel.OnQueryTextChanged
import com.github.vitkidd.geosearch.feature.presentation.viewModel.OnRegionChanged
import com.github.vitkidd.geosearch.feature.presentation.viewModel.OnViewCreated
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(R.layout.fmt_main) {

    private val binding by viewBinding(FmtMainBinding::bind)
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var map: GoogleMap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cameraPosition = CameraPosition.Builder()
            .target(LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE))
            .zoom(INITIAL_ZOOM)
            .build()

        val options = GoogleMapOptions()
            .zoomControlsEnabled(true)
            .zoomGesturesEnabled(true)
            .camera(cameraPosition)

        val mapFragment = SupportMapFragment.newInstance(options)

        childFragmentManager.commitNow {
            add(R.id.container, mapFragment, "SupportMapFragment")
        }

        mapFragment.getMapAsync(::mapReady)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String?): Boolean {
                mainViewModel.onViewEvent(OnQueryTextChanged(newText))
                return true
            }
        })

        mainViewModel.onViewEvent(OnViewCreated)
        mainViewModel.state.observe(viewLifecycleOwner, ::render)
    }

    private fun render(mainViewState: MainViewState) {
        when (mainViewState) {
            is MainViewState.Data -> dataState(mainViewState)
            MainViewState.Empty -> emptyState()
            MainViewState.Error -> errorState()
        }
    }

    private var targets = mutableListOf<MarkerTarget>()

    private fun dataState(state: MainViewState.Data) {
        clearMap()
        targets.addAll(state.photos.map { MarkerTarget(map, it) })
        targets.forEach(::getMarkerIcon)
    }

    private fun getMarkerIcon(target: MarkerTarget) {
        Glide.with(requireContext())
            .asBitmap()
            .load(target.photoModel.urlSmall)
            .into(target)
    }

    private fun emptyState() = clearMap()

    private fun errorState() {
        Snackbar.make(binding.main, R.string.error_search, Snackbar.LENGTH_SHORT)
    }

    private fun clearMap() {
        targets.forEach { Glide.with(requireContext()).clear(it) }
        targets.clear()
        map.clear()
    }

    private fun mapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setOnCameraIdleListener(::onRegionChanged)
        map.setOnMarkerClickListener(::onMarkerClicked)
    }

    private fun onRegionChanged() = with(map.projection.visibleRegion) {
        mainViewModel.onViewEvent(
            OnRegionChanged(
                RegionModel(
                    minLongitude = nearLeft.longitude,
                    minLatitude = nearLeft.latitude,
                    maxLongitude = farRight.longitude,
                    maxLatitude = farRight.latitude,
                )
            )
        )
    }

    private fun onMarkerClicked(marker: Marker): Boolean {
        (marker.tag as? PhotoModel)?.let {
            PhotoDialog.show(childFragmentManager, it)
        }

        return true
    }

    private companion object {
        private const val INITIAL_ZOOM = 16.0f
    }
}