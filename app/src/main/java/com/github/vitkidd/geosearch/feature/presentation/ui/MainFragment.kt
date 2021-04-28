package com.github.vitkidd.geosearch.feature.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.vitkidd.geosearch.R
import com.github.vitkidd.geosearch.databinding.FmtMainBinding
import com.github.vitkidd.geosearch.feature.presentation.model.RegionModel
import com.github.vitkidd.geosearch.feature.di.DEFAULT_LATITUDE
import com.github.vitkidd.geosearch.feature.di.DEFAULT_LONGITUDE
import com.github.vitkidd.geosearch.feature.presentation.model.MainViewState
import com.github.vitkidd.geosearch.feature.presentation.viewModel.MainViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
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
                mainViewModel.onQueryTextChange(newText)
                return true
            }
        })

        mainViewModel.subscribe()
        mainViewModel.state().observe(viewLifecycleOwner, ::render)
    }

    private fun render(mainViewState: MainViewState) {

    }

    private fun mapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setOnCameraIdleListener(::onRegionChanged)
    }

    private fun onRegionChanged() {
        val mapRegion = map.projection.visibleRegion.let {
            RegionModel(
                minLongitude = it.nearLeft.longitude,
                minLatitude = it.nearLeft.latitude,
                maxLongitude = it.farRight.longitude,
                maxLatitude = it.farRight.latitude
            )
        }

        mainViewModel.onRegionChanged(mapRegion)
    }

    private companion object {
        private const val INITIAL_ZOOM = 16.0f
    }
}