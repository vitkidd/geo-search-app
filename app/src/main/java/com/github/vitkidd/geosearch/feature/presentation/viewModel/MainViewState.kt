package com.github.vitkidd.geosearch.feature.presentation.viewModel

import com.github.vitkidd.geosearch.feature.presentation.model.PhotoModel

sealed interface MainViewState

object ErrorState : MainViewState

object EmptyState : MainViewState

data class DataState(val photos: List<PhotoModel>) : MainViewState
