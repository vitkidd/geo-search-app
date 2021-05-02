package com.github.vitkidd.geosearch.presentation

import com.github.vitkidd.geosearch.BaseTest
import com.github.vitkidd.geosearch.feature.domain.interactor.MainInteractor
import com.github.vitkidd.geosearch.feature.presentation.model.MainViewState
import com.github.vitkidd.geosearch.feature.presentation.viewModel.MainViewModel
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
    private val photoEntities = photoEntities()
    private val regionModel = regionModel()
    private val query = "Motorcycles"

    override fun setUp() {
        super.setUp()

        mainViewModel = MainViewModel(mainInteractor)
        mainViewModel.subscribe()
    }

    @Test
    fun errorState() {
        val exception = Throwable()
        whenever(mainInteractor.searchPhotos(any())).thenReturn(Observable.error(exception))

        mainViewModel.onQueryTextChange(query)
        mainViewModel.onRegionChanged(regionModel)

        val stateValue = mainViewModel.state().value

        assertThat(stateValue).isInstanceOf(MainViewState.Error::class.java)
        assertThat(stateValue).isNotNull
    }

    @Test
    fun emptyState() {
        whenever(mainInteractor.searchPhotos(any())).thenReturn(Observable.just(listOf()))

        mainViewModel.onQueryTextChange(query)
        mainViewModel.onRegionChanged(regionModel)

        val stateValue = mainViewModel.state().value

        assertThat(stateValue).isInstanceOf(MainViewState.Empty::class.java)
        assertThat(stateValue).isNotNull
    }

    @Test
    fun successState() {
        whenever(mainInteractor.searchPhotos(any())).thenReturn(Observable.just(photoEntities))

        mainViewModel.onQueryTextChange(query)
        mainViewModel.onRegionChanged(regionModel)

        val stateValue = mainViewModel.state().value

        assertThat(stateValue).isInstanceOf(MainViewState.Data::class.java)
        assertThat((stateValue as? MainViewState.Data)?.photos).isEqualTo(photoModels())
        assertThat(stateValue).isNotNull
    }
}