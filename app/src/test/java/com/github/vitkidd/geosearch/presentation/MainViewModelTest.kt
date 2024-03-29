package com.github.vitkidd.geosearch.presentation

import com.github.vitkidd.geosearch.BaseTest
import com.github.vitkidd.geosearch.feature.domain.interactor.MainInteractor
import com.github.vitkidd.geosearch.feature.presentation.mapper.PhotoModelMapper
import com.github.vitkidd.geosearch.feature.presentation.mapper.RegionEntityMapper
import com.github.vitkidd.geosearch.feature.presentation.mapper.SearchEntityMapper
import com.github.vitkidd.geosearch.feature.presentation.viewModel.*
import com.github.vitkidd.geosearch.photoEntities
import com.github.vitkidd.geosearch.photoModels
import com.github.vitkidd.geosearch.regionModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.assertj.core.api.Assertions.*
import org.junit.Test

internal class MainViewModelTest : BaseTest() {

    private lateinit var mainViewModel: MainViewModel
    private val mainInteractor: MainInteractor = mock()
    private val photoModelMapper = PhotoModelMapper()
    private val regionEntityMapper = RegionEntityMapper()
    private val searchEntityMapper = SearchEntityMapper(regionEntityMapper)
    private val photoEntities = photoEntities()
    private val regionModel = regionModel()
    private val query = "Motorcycles"

    override fun setUp() {
        super.setUp()

        mainViewModel = MainViewModel(mainInteractor, photoModelMapper, searchEntityMapper)
        mainViewModel.onViewEvent(OnViewCreated)
    }

    @Test
    fun errorState() {
        val exception = Throwable()
        whenever(mainInteractor.searchPhotos(any())).thenReturn(Observable.error(exception))

        mainViewModel.onViewEvent(OnQueryTextChanged(query))
        mainViewModel.onViewEvent(OnRegionChanged(regionModel))

        val stateValue = mainViewModel.state.value

        assertThat(stateValue).isInstanceOf(ErrorState::class.java)
        assertThat(stateValue).isNotNull
    }

    @Test
    fun emptyState() {
        whenever(mainInteractor.searchPhotos(any())).thenReturn(Observable.just(listOf()))

        mainViewModel.onViewEvent(OnQueryTextChanged(query))
        mainViewModel.onViewEvent(OnRegionChanged(regionModel))

        val stateValue = mainViewModel.state.value

        assertThat(stateValue).isInstanceOf(EmptyState::class.java)
        assertThat(stateValue).isNotNull
    }

    @Test
    fun successState() {
        whenever(mainInteractor.searchPhotos(any())).thenReturn(Observable.just(photoEntities))

        mainViewModel.onViewEvent(OnQueryTextChanged(query))
        mainViewModel.onViewEvent(OnRegionChanged(regionModel))

        val stateValue = mainViewModel.state.value

        assertThat(stateValue).isInstanceOf(DataState::class.java)
        assertThat((stateValue as? DataState)?.photos).isEqualTo(photoModels())
        assertThat(stateValue).isNotNull
    }
}