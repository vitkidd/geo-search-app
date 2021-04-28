package com.github.vitkidd.geosearch.feature.presentation.model

sealed class MainViewState {
    object Error : MainViewState()
    object Empty : MainViewState()
    data class Data(val photos: List<PhotoModel>) : MainViewState()
}
