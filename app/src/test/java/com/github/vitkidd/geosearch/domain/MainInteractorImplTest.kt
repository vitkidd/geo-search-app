package com.github.vitkidd.geosearch.domain

import com.github.vitkidd.geosearch.BaseTest
import com.github.vitkidd.geosearch.feature.data.repo.MainRepository
import com.github.vitkidd.geosearch.feature.domain.interactor.MainInteractorImpl
import com.github.vitkidd.geosearch.feature.domain.mapper.PhotoEntityMapper
import com.github.vitkidd.geosearch.photoEntities
import com.github.vitkidd.geosearch.searchEntity
import com.github.vitkidd.geosearch.searchResultDto
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Test

internal class MainInteractorImplTest: BaseTest() {

    private lateinit var interactor: MainInteractorImpl
    private val mainRepository: MainRepository = mock()
    private val photoEntityMapper = PhotoEntityMapper()
    private val region = "4.63144775480032,52.376537420804254,4.640275910496712,52.38637202434913"
    private val query = "motorcycle"
    private val searchEntity = searchEntity()
    private val photoEntities = photoEntities()
    private val searchResultDto = searchResultDto()

    override fun setUp() {
        super.setUp()

        interactor = MainInteractorImpl(mainRepository, photoEntityMapper)
    }

    @Test
    fun searchPhotos_success() {
        whenever(mainRepository.searchPhotos(any(), any())).thenReturn(Observable.just(searchResultDto.photos.photo))

        interactor.searchPhotos(searchEntity).test().apply {
            assertSubscribed()
            assertComplete()
            assertValue(photoEntities)
            assertNoErrors()
        }
    }

    @Test
    fun searchPhotos_failed() {
        val exceptionStub = Exception()
        whenever(mainRepository.searchPhotos(any(), any())).thenReturn(Observable.error(exceptionStub))

        interactor.searchPhotos(searchEntity).test().apply {
            assertSubscribed()
            assertNotComplete()
            assert(errorCount() == 1)
            assertNoValues()
            assertError(exceptionStub)
        }
    }
}