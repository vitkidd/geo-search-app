package com.github.vitkidd.geosearch.feature.presentation.viewModel

import com.github.vitkidd.geosearch.feature.presentation.model.PhotoModel

sealed interface MainViewState {
    object Error : MainViewState
    object Empty : MainViewState
    data class Data(val photos: List<PhotoModel>) : MainViewState
}
