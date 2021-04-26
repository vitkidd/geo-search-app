package com.github.vitkidd.geosearch.feature

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.vitkidd.geosearch.R
import com.github.vitkidd.geosearch.databinding.FmtMainBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment

class MainFragment : Fragment(R.layout.fmt_main) {

    private val binding by viewBinding(FmtMainBinding::bind)
    private lateinit var map: GoogleMap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = SupportMapFragment.newInstance()

        childFragmentManager.commitNow {
            add(R.id.container, mapFragment, "SupportMapFragment")
        }

        mapFragment.getMapAsync(::mapReady)
    }

    private fun mapReady(googleMap: GoogleMap) {
        map = googleMap
    }
}