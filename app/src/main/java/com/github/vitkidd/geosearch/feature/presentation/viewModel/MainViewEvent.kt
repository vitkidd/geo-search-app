package com.github.vitkidd.geosearch.feature.presentation.viewModel

import com.github.vitkidd.geosearch.feature.presentation.model.RegionModel

sealed interface MainViewEvent

data class OnQueryTextChanged(val query: String?) : MainViewEvent

data class OnRegionChanged(val regionModel: RegionModel) : MainViewEvent

object OnViewCreated : MainViewEvent