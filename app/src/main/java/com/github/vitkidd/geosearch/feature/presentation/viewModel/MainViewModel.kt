package com.github.vitkidd.geosearch.feature.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.github.vitkidd.geosearch.entity.MapRegion
import com.github.vitkidd.geosearch.feature.data.network.Api
import com.github.vitkidd.geosearch.feature.di.DEFAULT_QUERY
import io.reactivex.subjects.BehaviorSubject

class MainViewModel(
    private val api: Api
) : ViewModel() {

    private val querySubject = BehaviorSubject.createDefault(DEFAULT_QUERY)
    private val regionSubject = BehaviorSubject.create<MapRegion>()

    fun onQueryTextChange(query: String? ) {
        querySubject.onNext(query ?: DEFAULT_QUERY)
    }

    fun onRegionChanged(mapRegion: MapRegion) {
        regionSubject.onNext(mapRegion)
    }
}